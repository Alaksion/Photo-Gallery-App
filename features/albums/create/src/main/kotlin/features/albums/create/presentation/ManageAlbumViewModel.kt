package features.albums.create.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import features.albums.create.presentation.steps.AlbumResult
import features.albums.shared.domain.model.CreateAlbumDTO
import features.albums.shared.domain.repository.AlbumRepository
import io.github.alaksion.MutableUiStateOwner
import io.github.alaksion.UiStateHandler
import io.github.alaksion.uievent.UiEvent
import io.github.alaksion.uievent.UiEventHandler
import io.github.alaksion.uievent.UiEventOwnerSender
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import platform.injection.IODispatcher
import platform.navigation.params.ManageAlbumOperation
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
) : ViewModel(), MutableUiStateOwner<ManageAlbumState> by UiStateHandler(ManageAlbumState()),
    UiEventOwnerSender<CreateAlbumEvents> by UiEventHandler() {

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
            updateState { updater ->
                updater.update { currentData ->
                    currentData.copy(
                        album = currentData.album.copy(name = value)
                    )
                }
            }
        }
    }

    private fun updateDescription(value: String) {
        updateState {
            updateState { updater ->
                updater.update { currentData ->
                    currentData.copy(
                        album = currentData.album.copy(description = value)
                    )
                }
            }
        }
    }

    private fun clearData() {
        updateState {
            updateState { updater ->
                updater.update { currentData ->
                    currentData.copy(
                        album = EmptyAlbum
                    )
                }
            }
        }
    }

    private fun loadAlbumData(albumId: Int) {
        viewModelScope.launch(dispatcher) {
            asyncUpdateState { updater ->
                val data = repository.getAlbumById(albumId)
                updater.update { state ->
                    state.copy(
                        album = data.album
                    )
                }
            }
        }
    }

    private fun createAlbum() {
        viewModelScope.launch(dispatcher) {
            asyncCatching {
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

                sendEvent(result)
            }
        }
    }

    private fun updateAlbum() {
        viewModelScope.launch(dispatcher) {
            asyncCatching {
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
                sendEvent(event)
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
