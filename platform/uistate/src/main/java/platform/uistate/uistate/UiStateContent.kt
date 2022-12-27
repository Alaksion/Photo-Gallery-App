package platform.uistate.uistate

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import platform.uicomponents.components.DefaultLoadingView

@Composable
fun <T> UiState<T>.UiStateContent(
    stateContent: @Composable (T) -> Unit,
    errorState: @Composable (Throwable) -> Unit,
    loadingState: @Composable () -> Unit = { DefaultLoadingView(modifier = Modifier.fillMaxSize()) },
) {
    when (this.uiState) {
        is UiStateType.Content -> stateContent(this.data)
        is UiStateType.Loading -> loadingState()
        is UiStateType.Error -> errorState(this.uiState.error)
    }

}
