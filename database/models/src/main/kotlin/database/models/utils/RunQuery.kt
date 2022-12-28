package database.models.utils

import platform.error.InternalException

@Suppress("TooGenericExceptionCaught")
internal suspend fun <T> runQuery(block: suspend () -> T): T {
    return try {
        block()
    } catch (e: InternalException.Generic) {
        throw e
    } catch (e: Throwable) {
        throw InternalException.Untreated(e)
    }
}
