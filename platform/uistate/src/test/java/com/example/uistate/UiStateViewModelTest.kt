package com.example.uistate

import app.cash.turbine.test
import com.google.common.truth.Truth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
internal class UiStateViewModelTest {

    private lateinit var viewModel: SampleUiViewModel
    private val fakeService = FakeService()
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        viewModel = SampleUiViewModel(
            fakeService = fakeService,
            dispatcher = dispatcher
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Should start with content state containing initial data`() = runTest(dispatcher) {

        viewModel.uiState.filterForData().test {
            val state = awaitItem()

            Truth.assertThat(state).isEqualTo(SampleState())

            cancelAndIgnoreRemainingEvents()
        }
    }


}