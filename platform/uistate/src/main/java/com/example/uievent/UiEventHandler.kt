package com.example.uievent

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

interface UiEventHandler<T : UiEvent> {

    val events: StateFlow<List<T>>

    suspend fun enqueueEvent(event: T)

    fun consumeEvent(event: T)

}

class UiEventHandlerImpl<T : UiEvent> : UiEventHandler<T> {

    private val eventQueue = MutableStateFlow<List<T>>(listOf())

    override val events: StateFlow<List<T>> = eventQueue.asStateFlow()

    override suspend fun enqueueEvent(event: T) {
        eventQueue.update { currentState ->
            currentState + event
        }
    }

    override fun consumeEvent(event: T) {
        eventQueue.update { currentState ->
            currentState.filter { it.eventId != event.eventId }
        }
    }

}