package br.com.alaksion.database.utils

internal suspend fun <T> runQuery(block: suspend () -> T): T {
    return try {
        block()
    } catch (e: Throwable) {
        throw e
    }
}