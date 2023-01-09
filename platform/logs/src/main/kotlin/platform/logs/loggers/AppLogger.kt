package platform.logs.loggers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import platform.logs.LogProvider

interface AppLogger {

    fun registerLog(
        type: LogType,
        logTag: String,
        content: String
    )

}

enum class LogType {
    Debug,
    Error,
    Warning,
}

@Composable
fun rememberAppLogger() = remember { LogProvider.provide() }
