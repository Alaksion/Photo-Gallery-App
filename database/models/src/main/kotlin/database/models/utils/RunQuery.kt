package database.models.utils

import com.example.error.InternalException

internal suspend fun <T> runQuery(block: suspend () -> T): T {
    return try {
        block()
    } catch (e: InternalException.Generic) {
        throw e
    } catch (e: Throwable) {
        throw InternalException.Untreated(e)
    }
}