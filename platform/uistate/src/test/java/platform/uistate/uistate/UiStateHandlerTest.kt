package platform.uistate.uistate

import app.cash.turbine.test
import com.google.common.truth.Truth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import platform.uistate.testData.SampleState

@ExperimentalCoroutinesApi
internal class UiStateHandlerTest {

    private val dispatcher = StandardTestDispatcher()

    private lateinit var handler: UiStateHandler<platform.uistate.testData.SampleState>

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        handler = UiStateHandler(SampleState())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Should update state when updateState is called`() = runTest {
        handler.updateState {
            updateData { current ->
                current.copy(text = "new")
            }
        }

        handler.uiState.filterForData().test {
            Truth.assertThat(awaitItem().text).isEqualTo("new")
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Should set type to error when updateState throws exception`() = runTest {
        handler.updateState {
            throw IllegalStateException()
        }

        handler.uiState.test {
            Truth.assertThat(awaitItem().uiState).isInstanceOf(UiStateType.Error::class.java)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Should display loading state when asyncUpdate is called with show loading`() = runTest {

        handler.uiState.test {

            handler.asyncUpdateState(showLoading = true) {
                updateData { current ->
                    current.copy(text = "new")
                }
            }

            skipItems(1)
            val loadingState = awaitItem()
            skipItems(1)
            val contentState = awaitItem()

            Truth.assertThat(loadingState.uiState).isEqualTo(UiStateType.Loading)
            Truth.assertThat(contentState.uiState).isEqualTo(UiStateType.Content)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Should not display loading state when asyncUpdate is called without show loading`() =
        runTest {

            handler.uiState.test {
                handler.asyncUpdateState(showLoading = false) {
                    updateData { current ->
                        current.copy(text = "new")
                    }
                }

                skipItems(1)
                val contentState = awaitItem()

                Truth.assertThat(contentState.uiState).isEqualTo(UiStateType.Content)
                cancelAndIgnoreRemainingEvents()
            }
        }


    @Test
    fun `Should update state when  asyncUpdateState is called`() = runTest {
        handler.asyncUpdateState {
            delay(300)
            updateData { current ->
                current.copy(text = "new")
            }
        }

        handler.uiState.filterForData().test {
            Truth.assertThat(awaitItem().text).isEqualTo("new")
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Should set type to error when asyncUpdateState throws exception`() = runTest {
        handler.asyncUpdateState {
            throw IllegalStateException()
        }

        handler.uiState.test {
            Truth.assertThat(awaitItem().uiState).isInstanceOf(UiStateType.Error::class.java)
            cancelAndIgnoreRemainingEvents()
        }
    }

}
