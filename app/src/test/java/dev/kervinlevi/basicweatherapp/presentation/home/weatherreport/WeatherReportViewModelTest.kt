package dev.kervinlevi.basicweatherapp.presentation.home.weatherreport

import dev.kervinlevi.basicweatherapp.MainDispatcherRule
import dev.kervinlevi.basicweatherapp.domain.authentication.AuthenticationRepository
import dev.kervinlevi.basicweatherapp.domain.location.LocationProvider
import dev.kervinlevi.basicweatherapp.domain.model.ApiResponse
import dev.kervinlevi.basicweatherapp.domain.model.WeatherReport
import dev.kervinlevi.basicweatherapp.domain.weather.WeatherRepository
import dev.kervinlevi.basicweatherapp.testdata.testLocation1
import dev.kervinlevi.basicweatherapp.testdata.testWeatherReport1
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
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
import java.net.UnknownHostException


/**
 * Created by kervinlevi on 20/11/24
 */
class WeatherReportViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    lateinit var weatherRepository: WeatherRepository

    @MockK
    lateinit var locationProvider: LocationProvider

    @MockK
    lateinit var authRepository: AuthenticationRepository

    private lateinit var viewModel: WeatherReportViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        every { authRepository.isLoggedIn() } returns false
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `Given location permission is granted, verify that location and weather is retrieved`() {
        every { locationProvider.isLocationPermissionGranted() } returns true
        coEvery { locationProvider.getLocation() } returns testLocation1
        coEvery { weatherRepository.getCurrentWeather(any()) } returns ApiResponse.Success(
            testWeatherReport1
        )

        viewModel = WeatherReportViewModel(locationProvider, weatherRepository, authRepository)

        coVerify { locationProvider.getLocation() }
        coVerify { weatherRepository.getCurrentWeather(testLocation1) }
        val output = viewModel.weatherReportState.value
        assertEquals(testWeatherReport1, output.weatherReport)
        assertEquals(testLocation1, output.location)
        assertFalse(output.isLoading)
        assertNull(output.error)
    }

    @Test
    fun `Given location permission is denied, verify that location and weather is not retrieved`() {
        every { locationProvider.isLocationPermissionGranted() } returns false
        coEvery { locationProvider.getLocation() } returns null
        coEvery { weatherRepository.getCurrentWeather(any()) } returns ApiResponse.Success(
            testWeatherReport1
        )

        viewModel = WeatherReportViewModel(locationProvider, weatherRepository, authRepository)

        coVerify(exactly = 0) { locationProvider.getLocation() }
        coVerify(exactly = 0) { weatherRepository.getCurrentWeather(testLocation1) }
        val output = viewModel.weatherReportState.value
        assertNull(output.weatherReport)
        assertNull(output.location)
        assertFalse(output.isLoading)
        assertEquals(WeatherReportError.LocationUnavailable, output.error)
    }

    @Test
    fun `Given isLoading true, verify that pull to refresh doesn't call api multiple times`() {
        every { locationProvider.isLocationPermissionGranted() } returns true
        coEvery { locationProvider.getLocation() } returns testLocation1
        coEvery { weatherRepository.getCurrentWeather(any()) } returns ApiResponse.Success(
            testWeatherReport1
        )

        viewModel = WeatherReportViewModel(locationProvider, weatherRepository, authRepository)
        viewModel.weatherReportState.value =
            viewModel.weatherReportState.value.copy(isLoading = true)
        viewModel.onAction(WeatherReportAction.OnPullToRefresh)
        viewModel.onAction(WeatherReportAction.OnPullToRefresh)
        viewModel.onAction(WeatherReportAction.OnPullToRefresh)

        coVerify(exactly = 1) { locationProvider.getLocation() }
        coVerify(exactly = 1) { weatherRepository.getCurrentWeather(testLocation1) }
    }

    @Test
    fun `Verify state after onAction ShowPermissionsRationale`() {
        every { locationProvider.isLocationPermissionGranted() } returns true
        coEvery { locationProvider.getLocation() } returns testLocation1
        coEvery { weatherRepository.getCurrentWeather(any()) } returns ApiResponse.Success(
            testWeatherReport1
        )

        viewModel = WeatherReportViewModel(locationProvider, weatherRepository, authRepository)
        viewModel.onAction(WeatherReportAction.ShowPermissionsRationale)

        val output = viewModel.weatherReportState.value
        assertTrue(output.showLocationPermissionRationale)
        assertFalse(output.requestLocationPermissions)
    }

    @Test
    fun `Verify state and method invocation after onAction PermissionGranted`() {
        every { locationProvider.isLocationPermissionGranted() } returns false
        coEvery { locationProvider.getLocation() } returns testLocation1
        coEvery { weatherRepository.getCurrentWeather(any()) } returns ApiResponse.Success(
            testWeatherReport1
        )

        viewModel = WeatherReportViewModel(locationProvider, weatherRepository, authRepository)
        viewModel.onAction(WeatherReportAction.PermissionGranted)

        coVerify(exactly = 1) { locationProvider.getLocation() }
        coVerify(exactly = 1) { weatherRepository.getCurrentWeather(testLocation1) }
        val output = viewModel.weatherReportState.value
        assertEquals(testWeatherReport1, output.weatherReport)
        assertEquals(testLocation1, output.location)
        assertFalse(output.isLoading)
        assertNull(output.error)
    }

    @Test
    fun `Given location permission is granted but location is null, verify state`() {
        every { locationProvider.isLocationPermissionGranted() } returns true
        coEvery { locationProvider.getLocation() } returns null
        coEvery { weatherRepository.getCurrentWeather(any()) } returns ApiResponse.Success(
            testWeatherReport1
        )

        viewModel = WeatherReportViewModel(locationProvider, weatherRepository, authRepository)

        coVerify(exactly = 1) { locationProvider.getLocation() }
        coVerify(exactly = 0) { weatherRepository.getCurrentWeather(any()) }
        val output = viewModel.weatherReportState.value
        assertNull(output.weatherReport)
        assertNull(output.location)
        assertFalse(output.isLoading)
        assertEquals(WeatherReportError.LocationUnavailable, output.error)
    }

    @Test
    fun `Given getCurrentWeather returns an http error, verify state`() {
        every { locationProvider.isLocationPermissionGranted() } returns true
        coEvery { locationProvider.getLocation() } returns testLocation1
        coEvery { weatherRepository.getCurrentWeather(any()) } returns ApiResponse.Error(
            HttpException(Response.error<WeatherReport>(401, "Unauthorized".toResponseBody()))
        )

        viewModel = WeatherReportViewModel(locationProvider, weatherRepository, authRepository)

        coVerify(exactly = 1) { locationProvider.getLocation() }
        coVerify(exactly = 1) { weatherRepository.getCurrentWeather(testLocation1) }
        val output = viewModel.weatherReportState.value
        assertNull(output.weatherReport)
        assertEquals(testLocation1, output.location)
        assertFalse(output.isLoading)
        assertTrue(output.error is WeatherReportError.HttpError)
    }

    @Test
    fun `Given getCurrentWeather returns an unknown host exception, verify state`() {
        every { locationProvider.isLocationPermissionGranted() } returns true
        coEvery { locationProvider.getLocation() } returns testLocation1
        coEvery { weatherRepository.getCurrentWeather(any()) } returns ApiResponse.Error(
            UnknownHostException()
        )

        viewModel = WeatherReportViewModel(locationProvider, weatherRepository, authRepository)

        coVerify(exactly = 1) { locationProvider.getLocation() }
        coVerify(exactly = 1) { weatherRepository.getCurrentWeather(testLocation1) }
        val output = viewModel.weatherReportState.value
        assertNull(output.weatherReport)
        assertEquals(testLocation1, output.location)
        assertFalse(output.isLoading)
        assertTrue(output.error is WeatherReportError.NoInternet)
    }

    @Test
    fun `Verify state after onAction LogOut`() {
        every { authRepository.isLoggedIn() } returns true
        every { locationProvider.isLocationPermissionGranted() } returns true
        coEvery { locationProvider.getLocation() } returns testLocation1
        coEvery { weatherRepository.getCurrentWeather(any()) } returns ApiResponse.Success(
            testWeatherReport1
        )

        viewModel = WeatherReportViewModel(locationProvider, weatherRepository, authRepository)
        viewModel.onAction(WeatherReportAction.LogOut)

        coVerify { authRepository.logOut() }
        val output = viewModel.weatherReportState.value
        assertEquals(false, output.isLoggedIn)
    }
}
