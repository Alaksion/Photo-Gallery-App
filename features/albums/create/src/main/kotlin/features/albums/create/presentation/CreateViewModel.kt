package features.albums.create.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import features.albums.create.presentation.steps.AlbumResult
import features.albums.shared.domain.model.CreateAlbumDTO
import features.albums.shared.domain.repository.AlbumRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import platform.injection.IODispatcher
import platform.uistate.uievent.UiEvent
import platform.uistate.uievent.UiEventHandler
import platform.uistate.uievent.UiEventOwner
import platform.uistate.uistate.UiStateHandler
import platform.uistate.uistate.UiStateOwner
import javax.inject.Inject

internal sealed class CreateAlbumIntent {
    data class UpdateName(val value: String) : CreateAlbumIntent()
    data class UpdateDescription(val value: String) : CreateAlbumIntent()
    object CreateAlbum : CreateAlbumIntent()
    object ClearData : CreateAlbumIntent()
}

internal sealed class CreateAlbumEvents(val result: AlbumResult) : UiEvent() {
    class Result(
        result: AlbumResult,
    ) : CreateAlbumEvents(result)
}

@HiltViewModel
internal class CreateViewModel @Inject constructor(
    @IODispatcher private val dispatcher: CoroutineDispatcher,
    private val repository: AlbumRepository
) : ViewModel(), UiStateOwner<CreateAlbumState> by UiStateHandler(CreateAlbumState()),
    UiEventOwner<CreateAlbumEvents> by UiEventHandler() {

    fun handleIntent(intent: CreateAlbumIntent) {
        when (intent) {
            is CreateAlbumIntent.UpdateName -> updateName(intent.value)
            is CreateAlbumIntent.UpdateDescription -> updateDescription(intent.value)
            CreateAlbumIntent.CreateAlbum -> createAlbum()
            CreateAlbumIntent.ClearData -> clearData()
        }
    }

    private fun updateName(value: String) {
        updateState {
            updateData { currentState ->
                currentState.copy(name = value)
            }
        }
    }

    private fun updateDescription(value: String) {
        updateState {
            updateData { currentState ->
                currentState.copy(description = value)
            }
        }
    }

    private fun createAlbum() {
        viewModelScope.launch(dispatcher) {
            asyncRunCatching {
                val result = kotlin.runCatching {
                    repository.createAlbum(
                        data = CreateAlbumDTO(
                            name = stateData.name,
                            description = stateData.description
                        )
                    )
                }.fold(
                    onSuccess = {
                        clearData()
                        CreateAlbumEvents.Result(
                            AlbumResult.Success,
                        )
                    },
                    onFailure = { CreateAlbumEvents.Result(AlbumResult.Error) },
                )

                enqueueEvent(result)
            }
        }
    }

    private fun clearData() {
        updateState {
            updateData { state ->
                state.copy(
                    name = "",
                    description = "",
                )
            }
        }
    }

}
