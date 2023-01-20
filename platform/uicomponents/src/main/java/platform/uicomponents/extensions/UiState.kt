package platform.uicomponents.extensions

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import io.github.alaksion.UiState
import io.github.alaksion.UiStateType
import platform.logs.loggers.LogType
import platform.logs.loggers.rememberAppLogger
import platform.uicomponents.components.DefaultLoadingView

private const val UiStateErrorLogTag = "UiStateContentError"

@Composable
fun <T> UiState<T>.UiStateContent(
    stateContent: @Composable (T) -> Unit,
    errorState: @Composable (Throwable) -> Unit,
    loadingState: @Composable () -> Unit = { DefaultLoadingView(modifier = androidx.compose.ui.Modifier.fillMaxSize()) },
) {
    when (val type = this.stateType) {
        is UiStateType.Content -> stateContent(this.stateData)
        is UiStateType.Loading -> loadingState()
        is UiStateType.Error -> {
            val logger = rememberAppLogger()
            logger.registerLog(LogType.Error, UiStateErrorLogTag, type.error.toString())
            errorState(type.error)
        }
    }

}