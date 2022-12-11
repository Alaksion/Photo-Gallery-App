package com.example.mvisample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.uievent.UiEvent
import com.example.uievent.UiEventHandler
import com.example.uievent.UiEventHandlerImpl
import com.example.uistate.UiStateViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import java.util.UUID


internal enum class SampleEvents(override val eventId: UUID) : UiEvent {
    Hello(UUID.randomUUID()),
    World(UUID.randomUUID())
}

internal class MainViewModel(
    dispatcher: CoroutineDispatcher
) : UiStateViewModel<MainState>(MainState(), dispatcher),
    UiEventHandler<SampleEvents> by UiEventHandlerImpl() {

    fun handleIntent(intent: MainIntent) {
        when (intent) {
            is MainIntent.SendEvent -> receiveEvent()
            is MainIntent.UpdateText -> updateText(intent.text)
        }
    }

    private fun receiveEvent() {
        runSuspend {
            enqueueEvent(SampleEvents.Hello)
        }
    }

    private fun updateText(text: String) {
        setState(
            showLoading = false
        ) { currentState ->
            currentState.copy(mock = text)
        }
    }

}

class MainViewModelProvider : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val vm = MainViewModel(Dispatchers.Default)
        return vm as T
    }

}