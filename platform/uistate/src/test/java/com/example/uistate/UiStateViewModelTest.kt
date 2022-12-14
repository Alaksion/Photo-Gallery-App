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

    @Test
    fun `Should update state and call loadingState when setState is called`() =
        runTest(dispatcher) {

            viewModel.uiState.test {
                skipItems(1)

                viewModel.updateStateOnce(
                    text = "update",
                    number = 30,
                    boolean = false,
                    showLoading = true
                )

                val loadingState = awaitItem()
                val contentState = awaitItem()

                Truth.assertThat(loadingState).isInstanceOf(UiState.Loading::class.java)
                Truth.assertThat(contentState).isInstanceOf(UiState.Content::class.java)
                Truth.assertThat((contentState as UiState.Content).state).isEqualTo(
                    SampleState(
                        text = "update",
                        number = 30,
                        boolean = false
                    )
                )

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `Should update state and not call loadingState when setState is called with showLoading false`() =
        runTest(dispatcher) {

            viewModel.uiState.test {
                skipItems(1)

                viewModel.updateStateOnce(
                    text = "update",
                    number = 30,
                    boolean = false,
                    showLoading = false
                )

                val contentState = awaitItem()

                Truth.assertThat(contentState).isInstanceOf(UiState.Content::class.java)
                Truth.assertThat((contentState as UiState.Content).state).isEqualTo(
                    SampleState(
                        text = "update",
                        number = 30,
                        boolean = false
                    )
                )

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `Should set uiState to error when setState throws an unhandled exception`() =
        runTest(dispatcher) {
            viewModel.shouldThrowException = true

            viewModel.uiState.test {
                skipItems(1)

                viewModel.updateStateOnce(
                    text = "update",
                    number = 30,
                    boolean = false,
                    showLoading = false
                )

                val contentState = awaitItem()

                Truth.assertThat(contentState).isInstanceOf(UiState.Error::class.java)
                Truth.assertThat((contentState as UiState.Error).error)
                    .isInstanceOf(SampleException::class.java)

                cancelAndIgnoreRemainingEvents()
            }
        }


}