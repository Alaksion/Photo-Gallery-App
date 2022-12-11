package com.example.uistate

import androidx.compose.runtime.Composable

@Composable
fun <T> UiState<T>.UiStateContent(
    stateContent: @Composable (T) -> Unit,
    loadingState: @Composable () -> Unit,
    errorState: @Composable (Throwable) -> Unit
) {
    when (this) {
        is UiState.Content -> stateContent(this.state)
        is UiState.Loading -> loadingState()
        is UiState.Error -> errorState(this.error)
    }

}