package platform.test.fixtures

import platform.logs.loggers.AppLogger
import platform.logs.loggers.LogTag
import platform.logs.loggers.LogType

/**
 * Mock implementation of [AppLogger]. Calls of [registerLog] won't do anything.
 * */
class TestLogger : AppLogger {

    override fun registerLog(type: LogType, logTag: LogTag, content: String) = Unit

}
