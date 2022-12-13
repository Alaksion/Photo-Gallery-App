package com.example.uistate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class UiStateViewModel<T>(
    initialState: T,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val mutableUiState = MutableStateFlow<UiState<T>>(UiState.Content(initialState))

    val uiState: StateFlow<UiState<T>> = mutableUiState.asStateFlow()

    protected val currentStateData = MutableStateFlow(initialState)

    private val stateUpdater by lazy { StateManager(currentStateData) }

    /**
     * Use this function to update the UiState once. Any unhandled exception thrown will automatically update the
     * UiState to Error.
     *
     * @param showLoading Whether or not the loading state should be displayed while the suspend block is suspended. By default it's true.
     * @param block Suspend block responsible for the UiState update.
     * */
    protected fun setState(
        showLoading: Boolean = true,
        block: suspend (T) -> T,
    ) {
        if (showLoading) mutableUiState.value = UiState.Loading

        runSuspend {
            runCatching {
                block(currentStateData.value)
            }.fold(
                onSuccess = { newState ->
                    currentStateData.value = newState
                    mutableUiState.value = UiState.Content(currentStateData.value)
                },
                onFailure = { exception ->
                    mutableUiState.value = UiState.Error(exception)
                }
            )
        }
    }

    /**
     * Use this to update the state multiple times in a single block. Any unhandled exceptions will automatically
     * update the UiState to Error.
     *
     * @param showLoading Whether or not the loading state should be set at the start of the suspended block. By default it's true
     * @param block Suspend block which provides the usage of [StateManager], helper class that allows multiple state updates.
     * */
    protected fun stateUpdater(
        showLoading: Boolean = true,
        block: suspend (StateManager<T>) -> Unit
    ) {
        runSuspend {
            runCatching {
                if (showLoading) {
                    mutableUiState.value = UiState.Loading
                }
                block(stateUpdater)
            }.fold(
                onFailure = {
                    mutableUiState.value = UiState.Error(it)
                },
                onSuccess = {
                    mutableUiState.value = UiState.Content(currentStateData.value)
                }
            )
        }
    }

    protected fun runSuspend(
        block: suspend () -> Unit
    ) {
        viewModelScope.launch(dispatcher) {
            block()
        }
    }

}