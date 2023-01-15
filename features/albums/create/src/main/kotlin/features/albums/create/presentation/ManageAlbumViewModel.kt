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
import platform.navigation.params.ManageAlbumOperation
import platform.uistate.uievent.UiEvent
import platform.uistate.uievent.UiEventHandler
import platform.uistate.uievent.UiEventOwner
import platform.uistate.uistate.UiStateHandler
import platform.uistate.uistate.UiStateOwner
import javax.inject.Inject

internal sealed class CreateAlbumIntent {
    data class UpdateName(val value: String) : CreateAlbumIntent()
    data class UpdateDescription(val value: String) : CreateAlbumIntent()
    data class SubmitAlbum(val operation: ManageAlbumOperation) : CreateAlbumIntent()
    object ClearData : CreateAlbumIntent()
    data class LoadAlbumData(val albumInt: Int) : CreateAlbumIntent()
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
) : ViewModel(), UiStateOwner<ManageAlbumState> by UiStateHandler(ManageAlbumState()),
    UiEventOwner<CreateAlbumEvents> by UiEventHandler() {

    fun handleIntent(intent: CreateAlbumIntent) {
        when (intent) {
            is CreateAlbumIntent.UpdateName -> updateName(intent.value)
            is CreateAlbumIntent.UpdateDescription -> updateDescription(intent.value)
            is CreateAlbumIntent.SubmitAlbum -> submitAlbum(intent.operation)
            CreateAlbumIntent.ClearData -> clearData()
            is CreateAlbumIntent.LoadAlbumData -> loadAlbumData(intent.albumInt)
        }
    }

    private fun updateName(value: String) {
        updateState {
            updateData { currentState ->
                currentState.copy(
                    album = currentState.album.copy(name = value)
                )
            }
        }
    }

    private fun updateDescription(value: String) {
        updateState {
            updateData { currentState ->
                currentState.copy(
                    album = currentState.album.copy(description = value)
                )
            }
        }
    }

    private fun clearData() {
        updateState {
            updateData { state ->
                state.copy(
                    album = EmptyAlbum
                )
            }
        }
    }

    private fun loadAlbumData(albumId: Int) {
        viewModelScope.launch(dispatcher) {
            asyncUpdateState {
                val data = repository.getAlbumById(albumId)
                updateData { state ->
                    state.copy(
                        album = data.album
                    )
                }
            }
        }
    }

    private fun createAlbum() {
        viewModelScope.launch(dispatcher) {
            asyncRunCatching {
                val result = kotlin.runCatching {
                    repository.createAlbum(
                        data = CreateAlbumDTO(
                            name = stateData.album.name,
                            description = stateData.album.description
                        )
                    )
                }.fold(
                    onSuccess = {
                        CreateAlbumEvents.Result(
                            AlbumResult.CreateSuccess,
                        )
                    },
                    onFailure = { CreateAlbumEvents.Result(AlbumResult.Error) },
                )

                enqueueEvent(result)
            }
        }
    }

    private fun updateAlbum() {
        viewModelScope.launch(dispatcher) {
            asyncRunCatching {
                val event = kotlin.runCatching {
                    repository.updateAlbum(stateData.album)
                }.fold(
                    onSuccess = {
                        CreateAlbumEvents.Result(
                            AlbumResult.UpdateSuccess,
                        )
                    },
                    onFailure = { CreateAlbumEvents.Result(AlbumResult.Error) }
                )
                enqueueEvent(event)
            }
        }
    }

    private fun submitAlbum(operation: ManageAlbumOperation) {
        if (operation is ManageAlbumOperation.Edit) {
            updateAlbum()
        } else {
            createAlbum()
        }
    }

}
