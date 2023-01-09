package platform.logs.loggers

internal class SilentLogger : AppLogger {

    override fun registerLog(type: LogType, logTag: String, content: String) = Unit

}
