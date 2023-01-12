package features.photos.detail.presentation

import platform.uistate.uievent.UiEvent
import java.util.UUID

internal sealed class PhotoDetailsIntent {
    data class LoadData(val photoId: Int) : PhotoDetailsIntent()

    object DeletePhoto : PhotoDetailsIntent()
}

internal sealed class PhotoDetailsEvents : UiEvent {

    object DeleteSuccess : PhotoDetailsEvents() {
        override val eventId: UUID
            get() = UUID.randomUUID()
    }

    data class DeleteError(
        val message: String,
        override val eventId: UUID = UUID.randomUUID()
    ) : PhotoDetailsEvents()

}
