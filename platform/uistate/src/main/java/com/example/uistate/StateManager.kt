package com.example.uistate

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class StateManager<T>(
    private val state: MutableStateFlow<UiState<T>>
) {

    fun updateData(
        block: (T) -> T,
    ) {
        updateState(
            data = block(state.value.data),
            type = state.value.uiState
        )
    }

    fun updateStateType(type: UiStateType) {
        updateState(
            data = state.value.data,
            type = type
        )
    }

    fun updateState(
        data: T,
        type: UiStateType
    ) {
        state.update { currentState ->
            currentState.copy(
                data = data,
                uiState = type
            )
        }
    }


}