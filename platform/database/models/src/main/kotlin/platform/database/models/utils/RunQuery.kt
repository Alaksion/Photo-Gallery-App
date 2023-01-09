package platform.database.models.utils

import platform.error.InternalException
import platform.logs.loggers.AppLogger
import platform.logs.loggers.LogType

private const val RunQueryErrorLogTag = "RunQueryException"

@Suppress("TooGenericExceptionCaught")
internal suspend fun <T> runQuery(
    logger: AppLogger,
    block: suspend () -> T,
): T {
    return try {
        block()
    } catch (e: InternalException.Generic) {
        logger.registerLog(LogType.Error, RunQueryErrorLogTag, e.toString())
        throw e
    } catch (e: Throwable) {
        logger.registerLog(LogType.Error, RunQueryErrorLogTag, e.toString())
        throw InternalException.Untreated(e)
    }
}
