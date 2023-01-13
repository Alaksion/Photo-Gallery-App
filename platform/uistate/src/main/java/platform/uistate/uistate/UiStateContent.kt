package platform.uistate.uistate

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import platform.logs.loggers.LogType
import platform.logs.loggers.rememberAppLogger
import platform.uicomponents.components.DefaultLoadingView

private const val UiStateErrorLogTag = "UiStateContentError"

@Composable
fun <T> UiState<T>.UiStateContent(
    stateContent: @Composable (T) -> Unit,
    errorState: @Composable (Throwable) -> Unit,
    loadingState: @Composable () -> Unit = { DefaultLoadingView(modifier = Modifier.fillMaxSize()) },
) {
    when (this.uiState) {
        is UiStateType.Content -> stateContent(this.data)
        is UiStateType.Loading -> loadingState()
        is UiStateType.Error -> {
            
            val logger = rememberAppLogger()
            logger.registerLog(LogType.Error, UiStateErrorLogTag, this.uiState.error.toString())

            errorState(this.uiState.error)
        }
    }

}
