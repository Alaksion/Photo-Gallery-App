package com.example.uievent

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

interface UiEventHandler<T : UiEvent> {

    /**
     * Events held by this handler
     * */
    val events: StateFlow<List<T>>

    /**
     * Enqueue a new event to the UiEvents queue
     * */
    suspend fun ViewModel.enqueueEvent(event: T)

    /**
     * Notify the handler to remove an event that has been consumed
     * */
    fun consumeEvent(event: T)

}

class UiEventHandlerImpl<T : UiEvent> : UiEventHandler<T> {

    private val eventQueue = MutableStateFlow<List<T>>(listOf())

    override val events: StateFlow<List<T>> = eventQueue.asStateFlow()

    override suspend fun ViewModel.enqueueEvent(event: T) {
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