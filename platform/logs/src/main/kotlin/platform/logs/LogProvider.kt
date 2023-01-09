package platform.logs

import platform.logs.loggers.AppLogger
import platform.logs.loggers.DebugLogger
import platform.logs.loggers.SilentLogger

internal object LogProvider {

    fun provide(): AppLogger = if (BuildConfig.DEBUG) DebugLogger() else SilentLogger()

}
