package com.example.uistate

sealed class UiState {

    data class Content<T>(val state: T) : UiState()
    object Loading : UiState()
    data class Error(val error: Throwable) : UiState()

}
