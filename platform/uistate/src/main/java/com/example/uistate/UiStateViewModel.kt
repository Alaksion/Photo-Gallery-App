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

    protected val currentState = MutableStateFlow(initialState)

    protected fun setState(
        showLoading: Boolean = true,
        block: suspend (T) -> T,
    ) {
        if (showLoading) mutableUiState.value = UiState.Loading

        runSuspend {
            runCatching {
                block(currentState.value)
            }.fold(
                onSuccess = { newState ->
                    currentState.value = newState
                    mutableUiState.value = UiState.Content(newState)
                },
                onFailure = { exception ->
                    mutableUiState.value = UiState.Error(exception)
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