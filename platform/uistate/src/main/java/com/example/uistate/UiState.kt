package com.example.uistate

sealed class UiStateType {

    object Content : UiStateType()
    object Loading : UiStateType()
    data class Error(val error: Throwable) : UiStateType()

}


data class UiState<T>(
    val data: T,
    val uiState: UiStateType
)