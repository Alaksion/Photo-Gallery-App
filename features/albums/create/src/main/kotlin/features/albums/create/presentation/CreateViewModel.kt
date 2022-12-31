package features.albums.create.presentation

import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import features.albums.create.presentation.steps.AlbumResult
import features.albums.shared.domain.model.CreateAlbumDTO
import features.albums.shared.domain.repository.AlbumRepository
import kotlinx.coroutines.CoroutineDispatcher
import platform.injection.IODispatcher
import platform.uistate.uievent.UiEvent
import platform.uistate.uievent.UiEventHandler
import platform.uistate.uievent.UiEventHandlerImpl
import platform.uistate.uistate.UiStateViewModel
import java.util.UUID
import javax.inject.Inject

internal sealed class CreateAlbumIntent {
    data class UpdateName(val value: String) : CreateAlbumIntent()
    data class UpdateDescription(val value: String) : CreateAlbumIntent()

    data class UpdateLocation(val value: LatLng) : CreateAlbumIntent()
    object CreateAlbum : CreateAlbumIntent()
}

internal sealed class CreateAlbumEvents(val result: AlbumResult) : UiEvent {
    class Result(
        result: AlbumResult,
        override val eventId: UUID
    ) : CreateAlbumEvents(result)
}

@HiltViewModel
internal class CreateViewModel @Inject constructor(
    @IODispatcher dispatcher: CoroutineDispatcher,
    private val repository: AlbumRepository
) : UiStateViewModel<CreateAlbumState>(CreateAlbumState(), dispatcher),
    UiEventHandler<CreateAlbumEvents> by UiEventHandlerImpl() {

    fun handleIntent(intent: CreateAlbumIntent) {
        when (intent) {
            is CreateAlbumIntent.UpdateName -> updateName(intent.value)
            is CreateAlbumIntent.UpdateDescription -> updateDescription(intent.value)
            CreateAlbumIntent.CreateAlbum -> createAlbum()
            is CreateAlbumIntent.UpdateLocation -> updateLocation(intent.value)
        }
    }

    private fun updateName(value: String) {
        setState(showLoading = false) { currentState ->
            currentState.copy(name = value)
        }
    }

    private fun updateDescription(value: String) {
        setState(showLoading = false) { currentState ->
            currentState.copy(description = value)
        }
    }

    private fun createAlbum() {
        runSuspendCatching {
            val result = kotlin.runCatching {
                repository.createAlbum(
                    data = CreateAlbumDTO(
                        name = stateData.name,
                        description = stateData.description
                    )
                )
            }.fold(
                onSuccess = { CreateAlbumEvents.Result(AlbumResult.Success, UUID.randomUUID()) },
                onFailure = { CreateAlbumEvents.Result(AlbumResult.Error, UUID.randomUUID()) },
            )

            enqueueEvent(result)
        }
    }

    private fun updateLocation(location: LatLng) {
        setState(showLoading = false) { state ->
            state.copy(location = location)
        }
    }

}
