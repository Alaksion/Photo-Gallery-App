package com.example.error

sealed class InternalException : Throwable() {

    data class Generic(
        val text: String
    ) : InternalException()

}