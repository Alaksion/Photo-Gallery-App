package platform.uicomponents.sideeffects

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import io.github.alaksion.uievent.UiEvent
import io.github.alaksion.uievent.UiEventOwner
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Launched Effect wrapper to consume UiEvents from an UiEventHandler safely
 * @param eventHandler model class that implements UiEventHandler interface
 * @param onEventReceived callback called everytime an event is consumed
 * */
@Composable
fun <T : UiEvent> UiEventEffect(
    eventHandler: UiEventOwner<T>,
    onEventReceived: (T) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(key1 = eventHandler, key2 = lifecycleOwner) {
        eventHandler.eventQueue.collectEvents(
            lifecycleOwner = lifecycleOwner,
            onEventReceived = {
                onEventReceived(it)
                eventHandler.consumeEvent(it)
            }
        )
    }
}

fun <T : UiEvent> StateFlow<List<T>>.collectEvents(
    onEventReceived: suspend (T) -> Unit,
    lifecycleOwner: LifecycleOwner
) {
    lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            this@collectEvents.collect { eventQueue ->
                eventQueue.firstOrNull()?.let { event ->
                    onEventReceived(event)
                }
            }
        }
    }
}

