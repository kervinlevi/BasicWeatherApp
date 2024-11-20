package dev.kervinlevi.basicweatherapp.presentation.home.history

import dev.kervinlevi.basicweatherapp.MainDispatcherRule
import dev.kervinlevi.basicweatherapp.domain.weather.WeatherRepository
import dev.kervinlevi.basicweatherapp.testdata.testPastReport1
import dev.kervinlevi.basicweatherapp.testdata.testPastReport2
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Rule
import org.junit.Test


/**
 * Created by kervinlevi on 20/11/24
 */

class HistoryViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    lateinit var weatherRepository: WeatherRepository

    private lateinit var viewModel: HistoryViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `Given non-empty list from repository, verify the state`() {
        coEvery { weatherRepository.getPastReports() } returns listOf(
            testPastReport1,
            testPastReport2
        )
        viewModel = HistoryViewModel(weatherRepository)

        val result = viewModel.historyState.value
        assertEquals(2, result.weatherReports.size)
        assertFalse(result.isLoading)
    }

    @Test
    fun `Given empty list from repository, verify the state`() {
        coEvery { weatherRepository.getPastReports() } returns emptyList()
        viewModel = HistoryViewModel(weatherRepository)

        val output = viewModel.historyState.value
        assertEquals(0, output.weatherReports.size)
        assertFalse(output.isLoading)
    }
}
