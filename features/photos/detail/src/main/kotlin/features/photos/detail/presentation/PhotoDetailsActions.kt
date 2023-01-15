package features.photos.detail.presentation

import platform.uistate.uievent.UiEvent

internal sealed class PhotoDetailsIntent {
    data class LoadData(val photoId: Int) : PhotoDetailsIntent()

    object DeletePhoto : PhotoDetailsIntent()
}

internal sealed class PhotoDetailsEvents : UiEvent() {

    object DeleteSuccess : PhotoDetailsEvents()
    data class DeleteError(
        val message: String,
    ) : PhotoDetailsEvents()

}
