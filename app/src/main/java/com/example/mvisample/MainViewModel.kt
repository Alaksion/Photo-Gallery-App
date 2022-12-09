package com.example.mvisample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uievent.UiEvent
import com.example.uievent.UiEventHandler
import com.example.uievent.UiEventHandlerImpl
import kotlinx.coroutines.launch
import java.util.UUID


internal enum class SampleEvents(override val eventId: UUID) : UiEvent {
    Hello(UUID.randomUUID()),
    World(UUID.randomUUID())
}

internal class MainViewModel() :
    ViewModel(),
    UiEventHandler<SampleEvents> by UiEventHandlerImpl() {

    fun receiveEvent() {
        viewModelScope.launch {
            enqueueEvent(SampleEvents.Hello)
        }
    }

}