package com.example.uistate

import kotlinx.coroutines.flow.MutableStateFlow

class StateManager<T>(
    private val state: MutableStateFlow<T>
) {

    fun update(
        newState: T,
    ) {
        state.value = newState
    }

}