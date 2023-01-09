package platform.logs

import platform.logs.loggers.AppLogger
import platform.logs.loggers.DebugLogger
import platform.logs.loggers.SilentLogger

/**
 * Provides an instance of [AppLogger] depending on the currently active build variant.
 * */
internal object LogProvider {

    fun provide(): AppLogger = if (BuildConfig.DEBUG) DebugLogger() else SilentLogger()

}
