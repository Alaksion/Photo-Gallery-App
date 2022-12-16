package com.example.uistate

import app.cash.turbine.test
import com.google.common.truth.Truth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
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

        viewModel.uiState.test {
            val state = awaitItem()

            Truth.assertThat(state.data).isEqualTo(SampleState())
            Truth.assertThat(state.uiState).isEqualTo(UiStateType.Content)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Should update state and show loading state when setState is called`() =
        runTest(dispatcher) {

            viewModel.uiState.test {
                skipItems(1)

                viewModel.updateStateOnce(
                    text = "updated",
                    number = 9999,
                    boolean = true,
                    showLoading = true
                )

                val loadingState = awaitItem()
                val contentState = awaitItem()

                Truth.assertThat(loadingState.uiState).isEqualTo(UiStateType.Loading)

                Truth.assertThat(contentState.uiState).isEqualTo(UiStateType.Content)
                Truth.assertThat(contentState.data).isEqualTo(
                    SampleState(
                        text = "updated",
                        number = 9999,
                        boolean = true
                    )
                )
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `Should update state and ignore loading state when setState is called with loading = false`() =
        runTest(dispatcher) {
            viewModel.uiState.test {
                skipItems(1)

                viewModel.updateStateOnce(
                    text = "updated",
                    number = 9999,
                    boolean = true,
                    showLoading = false
                )

                val contentState = awaitItem()

                Truth.assertThat(contentState.uiState).isEqualTo(UiStateType.Content)
                Truth.assertThat(contentState.data).isEqualTo(
                    SampleState(
                        text = "updated",
                        number = 9999,
                        boolean = true
                    )
                )
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `Should update state to error when set state throws an unhandled exception`() =
        runTest(dispatcher) {

            viewModel.shouldThrowException = true

            viewModel.uiState.test {
                skipItems(1)

                viewModel.updateStateOnce(
                    text = "updated",
                    number = 9999,
                    boolean = true,
                    showLoading = false
                )

                val contentState = awaitItem()

                Truth.assertThat(contentState.uiState).isInstanceOf(UiStateType.Error::class.java)
                Truth.assertThat((contentState.uiState as UiStateType.Error).error)
                    .isInstanceOf(SampleException::class.java)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `Should run suspend code when runSuspendCatching is called`() = runTest(dispatcher) {
        viewModel.runSomethingSuspend()
        advanceUntilIdle()

        Truth.assertThat(viewModel.somethingSuspenseCalled).isTrue()
    }

    @Test
    fun `Should set loading state when runSuspendCatching is called`() = runTest(dispatcher) {
        viewModel.uiState.test {
            skipItems(1)

            viewModel.runSomethingSuspend(showLoadingState = true)

            val loadingState = awaitItem()
            val completionState = awaitItem()

            Truth.assertThat(loadingState.uiState).isEqualTo(UiStateType.Loading)
            Truth.assertThat(completionState.uiState).isEqualTo(UiStateType.Content)

            cancelAndIgnoreRemainingEvents()
        }

    }

    @Test
    fun `Should set state to error when unhandled exception is thrown inside runSuspendCatching`() =
        runTest(dispatcher) {
            viewModel.uiState.test {
                skipItems(1)

                viewModel.shouldThrowException = true

                viewModel.runSomethingSuspend(showLoadingState = false)

                val completionState = awaitItem()

                Truth.assertThat(completionState.uiState)
                    .isInstanceOf(UiStateType.Error::class.java)

                Truth.assertThat((completionState.uiState as UiStateType.Error).error)
                    .isInstanceOf(SampleException::class.java)

                cancelAndIgnoreRemainingEvents()
            }

        }

    @Test
    fun `Should update state multiple times when updateState is called`() =
        runTest(dispatcher) {
            viewModel.uiState.filterForData().test {
                skipItems(1)

                viewModel.updateStateMultipleTimes(showLoading = false)

                val state1 = awaitItem()
                val state2 = awaitItem()
                val state3 = awaitItem()

                Truth.assertThat(state1.number).isEqualTo(11)
                Truth.assertThat(state2.number).isEqualTo(12)
                Truth.assertThat(state3.number).isEqualTo(13)

                cancelAndConsumeRemainingEvents()
            }
        }

    @Test
    fun `Should show loading state when updateState is called with showloading true`() =
        runTest(dispatcher) {
            viewModel.uiState.test {
                skipItems(1)

                viewModel.updateStateMultipleTimes(showLoading = true)

                val loadingState = awaitItem()
                skipItems(3)
                val finalState = awaitItem()

                Truth.assertThat(loadingState.uiState).isEqualTo(UiStateType.Loading)

                Truth.assertThat(finalState.uiState).isEqualTo(UiStateType.Content)

                cancelAndConsumeRemainingEvents()
            }
        }

    @Test
    fun `Should not show loading state when updateState is called with showloading false`() =
        runTest(dispatcher) {
            viewModel.uiState.test {
                skipItems(1)

                viewModel.updateStateMultipleTimes(showLoading = false)

                skipItems(2)
                val finalState = awaitItem()

                Truth.assertThat(finalState.uiState).isEqualTo(UiStateType.Content)

                cancelAndConsumeRemainingEvents()
            }
        }

    @Test
    fun `Should set to error state when unhandled exception is thrown inside updateState`() =
        runTest(dispatcher) {
            viewModel.shouldThrowException = true

            viewModel.uiState.test {
                skipItems(1)

                viewModel.updateStateMultipleTimes(showLoading = false)

                val finalState = awaitItem()

                Truth.assertThat(finalState.uiState).isInstanceOf(UiStateType.Error::class.java)

                Truth.assertThat((finalState.uiState as UiStateType.Error).error)
                    .isInstanceOf(SampleException::class.java)

                cancelAndConsumeRemainingEvents()
            }
        }

}