package com.example.mvisample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.uievent.UiEvent
import com.example.uievent.UiEventHandler
import com.example.uievent.UiEventHandlerImpl
import com.example.uistate.UiStateViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID


internal enum class SampleEvents(override val eventId: UUID) : UiEvent {
    Hello(UUID.randomUUID()),
    World(UUID.randomUUID())
}

internal class MainViewModel(
    dispatcher: CoroutineDispatcher
) :
    UiStateViewModel<MainState>(MainState(), dispatcher),
    UiEventHandler<SampleEvents> by UiEventHandlerImpl() {

    fun receiveEvent() {
        viewModelScope.launch {
            enqueueEvent(SampleEvents.Hello)
        }
    }

}

class MainViewModelProvider : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val vm = MainViewModel(Dispatchers.Default)
        return vm as T
    }

}