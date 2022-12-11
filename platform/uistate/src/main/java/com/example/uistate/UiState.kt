package com.example.uistate

sealed class UiState<out T> {

    data class Content<T>(val state: T) : UiState<T>()
    object Loading : UiState<Nothing>()
    data class Error(val error: Throwable) : UiState<Nothing>()

}
