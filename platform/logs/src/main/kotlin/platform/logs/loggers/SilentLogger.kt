package platform.logs.loggers

import platform.logs.loggers.AppLogger
import platform.logs.loggers.LogType

internal class SilentLogger : AppLogger {

    override fun registerLog(type: LogType, logTag: String, content: String) = Unit

}
