package dev.kervinlevi.basicweatherapp.presentation.authentication.login

import dev.kervinlevi.basicweatherapp.MainDispatcherRule
import dev.kervinlevi.basicweatherapp.domain.authentication.AuthenticationRepository
import dev.kervinlevi.basicweatherapp.domain.model.ApiResponse
import dev.kervinlevi.basicweatherapp.domain.model.User
import dev.kervinlevi.basicweatherapp.presentation.authentication.login.LoginAction.LogIn
import dev.kervinlevi.basicweatherapp.presentation.authentication.login.LoginAction.UpdateEmail
import dev.kervinlevi.basicweatherapp.presentation.authentication.login.LoginAction.UpdatePassword
import dev.kervinlevi.basicweatherapp.testdata.testUser1
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

/**
 * Created by kervinlevi on 20/11/24
 */
class LoginViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    lateinit var authenticationRepository: AuthenticationRepository

    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        viewModel = LoginViewModel(authenticationRepository)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `Given correct inputs, verify that login is successful`() {
        coEvery { authenticationRepository.logIn(any(), any()) } returns ApiResponse.Success(
            testUser1
        )

        viewModel.onAction(UpdateEmail("dummyaccount@email.com"))
        viewModel.onAction(UpdatePassword("HkLhadlfaj4"))
        viewModel.onAction(LogIn)

        coVerify { authenticationRepository.logIn("dummyaccount@email.com", "HkLhadlfaj4") }
        val output = viewModel.loginState.value
        assertNull(output.emailError)
        assertNull(output.passwordError)
        assertTrue(output.hasLoggedIn)
    }

    @Test
    fun `Given correct inputs but has api error, verify that login is successful`() {
        coEvery { authenticationRepository.logIn(any(), any()) } returns ApiResponse.Error(
            HttpException(Response.error<User>(401, "Unauthorized".toResponseBody()))
        )

        viewModel.onAction(UpdateEmail("dummyaccount@email.com"))
        viewModel.onAction(UpdatePassword("HkLhadlfaj4"))
        viewModel.onAction(LogIn)

        coVerify { authenticationRepository.logIn("dummyaccount@email.com", "HkLhadlfaj4") }
        val output = viewModel.loginState.value
        assertNull(output.emailError)
        assertNull(output.passwordError)
        assertFalse(output.hasLoggedIn)
    }

    @Test
    fun `Given incorrect input, verify that login api is not called`() {
        coEvery { authenticationRepository.logIn(any(), any()) } returns ApiResponse.Success(
            testUser1
        )

        viewModel.onAction(UpdateEmail("wrong-email"))
        viewModel.onAction(UpdatePassword("HkLhadlfaj4"))
        viewModel.onAction(LogIn)

        coVerify(exactly = 0) { authenticationRepository.logIn(any(), any()) }
        val output = viewModel.loginState.value
        assertEquals(LoginFieldError.EmailWrongFormat, output.emailError)
        assertNull(output.passwordError)
        assertFalse(output.hasLoggedIn)
    }

    @Test
    fun `Test onAction UpdateEmail on different inputs`() {
        viewModel.onAction(UpdateEmail(""))
        assertTrue(viewModel.loginState.value.emailError is LoginFieldError.EmailEmpty)

        viewModel.onAction(UpdateEmail("dummyaccount@e"))
        assertTrue(viewModel.loginState.value.emailError is LoginFieldError.EmailWrongFormat)

        viewModel.onAction(UpdateEmail("dummyaccount @ e mail . com"))
        assertTrue(viewModel.loginState.value.emailError is LoginFieldError.EmailWrongFormat)

        viewModel.onAction(UpdateEmail("correct@format.com.ph"))
        assertNull(viewModel.loginState.value.emailError)
    }

    @Test
    fun `Test onAction UpdatePassword on different inputs`() {
        viewModel.onAction(UpdatePassword(""))
        assertTrue(viewModel.loginState.value.passwordError is LoginFieldError.PasswordEmpty)

        viewModel.onAction(UpdatePassword("short"))
        assertTrue(viewModel.loginState.value.passwordError is LoginFieldError.PasswordWrongFormat)

        viewModel.onAction(UpdatePassword("nouppercaseletter1"))
        assertTrue(viewModel.loginState.value.passwordError is LoginFieldError.PasswordWrongFormat)

        viewModel.onAction(UpdatePassword("NOLOWERCASELETTER2"))
        assertTrue(viewModel.loginState.value.passwordError is LoginFieldError.PasswordWrongFormat)

        viewModel.onAction(UpdatePassword("NoNumeric"))
        assertTrue(viewModel.loginState.value.passwordError is LoginFieldError.PasswordWrongFormat)

        viewModel.onAction(UpdatePassword("Corr3ctForm4t"))
        assertNull(viewModel.loginState.value.passwordError)
    }
}
