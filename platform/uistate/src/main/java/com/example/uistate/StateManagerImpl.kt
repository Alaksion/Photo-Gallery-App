package com.example.uistate

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

interface StateManager<T> {
    fun updateData(block: (T) -> T)

    fun updateStateType(type: UiStateType)

    fun updateState(block: (UiState<T>) -> UiState<T>)
}

class StateManagerImpl<T>(
    private val state: MutableStateFlow<UiState<T>>
) : StateManager<T> {

    override fun updateData(
        block: (T) -> T,
    ) {
        updateState { currentState ->
            currentState.copy(
                data = block(state.value.data)
            )
        }
    }

    override fun updateStateType(type: UiStateType) {
        updateState { currentState ->
            currentState.copy(uiState = type)
        }
    }

    override fun updateState(
        block: (UiState<T>) -> UiState<T>,
    ) {
        state.update { block(state.value) }
    }

}