package platform.uistate.uievent

import java.util.UUID

abstract class UiEvent {
    val eventId: UUID = UUID.randomUUID()
}
