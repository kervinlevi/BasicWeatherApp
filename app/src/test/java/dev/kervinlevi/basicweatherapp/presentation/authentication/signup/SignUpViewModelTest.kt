package dev.kervinlevi.basicweatherapp.presentation.authentication.signup

import dev.kervinlevi.basicweatherapp.MainDispatcherRule
import dev.kervinlevi.basicweatherapp.domain.authentication.AuthenticationRepository
import dev.kervinlevi.basicweatherapp.domain.model.ApiResponse
import dev.kervinlevi.basicweatherapp.domain.model.User
import dev.kervinlevi.basicweatherapp.presentation.authentication.signup.SignUpAction.SignUp
import dev.kervinlevi.basicweatherapp.presentation.authentication.signup.SignUpAction.UpdateEmail
import dev.kervinlevi.basicweatherapp.presentation.authentication.signup.SignUpAction.UpdateName
import dev.kervinlevi.basicweatherapp.presentation.authentication.signup.SignUpAction.UpdatePassword
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
class SignUpViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    lateinit var authRepository: AuthenticationRepository

    private lateinit var viewModel: SignUpViewModel


    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        viewModel = SignUpViewModel(authRepository)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `Given correct inputs, verify that sign up is successful`() {
        coEvery { authRepository.signUp(any(), any(), any()) } returns ApiResponse.Success(
            testUser1
        )

        viewModel.onAction(UpdateEmail("dummyaccount@email.com"))
        viewModel.onAction(UpdatePassword("HkLhadlfaj4"))
        viewModel.onAction(UpdateName("User"))
        viewModel.onAction(SignUp)

        coVerify { authRepository.signUp("dummyaccount@email.com", "User", "HkLhadlfaj4") }
        val output = viewModel.signUpState.value
        assertNull(output.emailError)
        assertNull(output.passwordError)
        assertNull(output.nameError)
        assertTrue(output.hasSignedUp)
    }

    @Test
    fun `Given correct inputs but has api error, verify that sign up is successful`() {
        coEvery { authRepository.signUp(any(), any(), any()) } returns ApiResponse.Error(
            HttpException(Response.error<User>(401, "Unauthorized".toResponseBody()))
        )

        viewModel.onAction(UpdateEmail("dummyaccount@email.com"))
        viewModel.onAction(UpdatePassword("HkLhadlfaj4"))
        viewModel.onAction(UpdateName("User"))
        viewModel.onAction(SignUp)

        coVerify { authRepository.signUp("dummyaccount@email.com", "User", "HkLhadlfaj4") }
        val output = viewModel.signUpState.value
        assertNull(output.emailError)
        assertNull(output.passwordError)
        assertNull(output.nameError)
        assertFalse(output.hasSignedUp)
    }

    @Test
    fun `Given incorrect input, verify that sign up api is not called`() {
        coEvery { authRepository.signUp(any(), any(), any()) } returns ApiResponse.Success(
            testUser1
        )

        viewModel.onAction(UpdateEmail("wrong-email"))
        viewModel.onAction(UpdatePassword("HkLhadlfaj4"))
        viewModel.onAction(UpdateName("User"))
        viewModel.onAction(SignUp)

        coVerify(exactly = 0) { authRepository.signUp(any(), any(), any()) }
        val output = viewModel.signUpState.value
        assertEquals(SignUpFieldError.EmailWrongFormat, output.emailError)
        assertNull(output.passwordError)
        assertNull(output.nameError)
        assertFalse(output.hasSignedUp)
    }

    @Test
    fun `Test onAction UpdateEmail on different inputs`() {
        viewModel.onAction(UpdateEmail(""))
        assertTrue(viewModel.signUpState.value.emailError is SignUpFieldError.EmailEmpty)

        viewModel.onAction(UpdateEmail("dummyaccount@e"))
        assertTrue(viewModel.signUpState.value.emailError is SignUpFieldError.EmailWrongFormat)

        viewModel.onAction(UpdateEmail("dummyaccount @ e mail . com"))
        assertTrue(viewModel.signUpState.value.emailError is SignUpFieldError.EmailWrongFormat)

        viewModel.onAction(UpdateEmail("correct@format.com.ph"))
        assertNull(viewModel.signUpState.value.emailError)
    }

    @Test
    fun `Test onAction UpdateName on different inputs`() {
        viewModel.onAction(UpdateName(""))
        assertTrue(viewModel.signUpState.value.nameError is SignUpFieldError.NameEmpty)

        viewModel.onAction(UpdateName("User KH"))
        assertNull(viewModel.signUpState.value.nameError)
    }

    @Test
    fun `Test onAction UpdatePassword on different inputs`() {
        viewModel.onAction(UpdatePassword(""))
        assertTrue(viewModel.signUpState.value.passwordError is SignUpFieldError.PasswordEmpty)

        viewModel.onAction(UpdatePassword("short"))
        assertTrue(viewModel.signUpState.value.passwordError is SignUpFieldError.PasswordWrongFormat)

        viewModel.onAction(UpdatePassword("nouppercaseletter1"))
        assertTrue(viewModel.signUpState.value.passwordError is SignUpFieldError.PasswordWrongFormat)

        viewModel.onAction(UpdatePassword("NOLOWERCASELETTER2"))
        assertTrue(viewModel.signUpState.value.passwordError is SignUpFieldError.PasswordWrongFormat)

        viewModel.onAction(UpdatePassword("NoNumeric"))
        assertTrue(viewModel.signUpState.value.passwordError is SignUpFieldError.PasswordWrongFormat)

        viewModel.onAction(UpdatePassword("Corr3ctForm4t"))
        assertNull(viewModel.signUpState.value.passwordError)
    }
}