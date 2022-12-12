package com.example.uistate

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.uicomponents.DefaultLoadingView

@Composable
fun <T> UiState<T>.UiStateContent(
    stateContent: @Composable (T) -> Unit,
    errorState: @Composable (Throwable) -> Unit,
    loadingState: @Composable () -> Unit = { DefaultLoadingView(modifier = Modifier.fillMaxSize()) },
) {
    when (this) {
        is UiState.Content -> stateContent(this.state)
        is UiState.Loading -> loadingState()
        is UiState.Error -> errorState(this.error)
    }

}