package platform.logs.loggers

import android.util.Log

internal class DebugLogger : AppLogger {

    override fun registerLog(type: LogType, logTag: String, content: String) {
        when (type) {
            LogType.Debug -> Log.d(logTag, content)
            LogType.Error -> Log.e(logTag, content)
            LogType.Warning -> Log.w(logTag, content)
        }
    }

}
