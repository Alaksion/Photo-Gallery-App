package platform.logs.loggers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import platform.logs.LogProvider

typealias LogTag = String

/**
 * Interface responsible for registering local logs. Use this class to help identifying important
 * actions on Logcat.
 * */
interface AppLogger {

    /**
     * Register a log entry in Log cat.
     * @param type Type of the log. Can be of types [LogType.Debug], [LogType.Error], [LogType.Warning]
     * @param logTag Identifier of the log entry, search for this tag in log cat to easily find
     * occurrences of a given log.
     * @param content Content to be logged.
     * */
    fun registerLog(
        type: LogType,
        logTag: LogTag,
        content: String
    )

}

enum class LogType {
    Debug,
    Error,
    Warning,
}

/**
 * Convenience function to access instances of [AppLogger] inside @Composable annotated scopes.
 * */
@Composable
fun rememberAppLogger() = remember { LogProvider.provide() }
