package br.com.alaksion.features.albums.presentation.create

import br.com.alaksion.features.albums.domain.model.CreateAlbumDTO
import br.com.alaksion.features.albums.domain.repository.AlbumRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import platform.injection.IODispatcher
import platform.uistate.uistate.UiStateViewModel
import javax.inject.Inject

internal sealed class CreateAlbumIntent {
    data class UpdateName(val value: String) : CreateAlbumIntent()
    data class UpdateDescription(val value: String) : CreateAlbumIntent()
    object CreateAlbum : CreateAlbumIntent()
}

@HiltViewModel
internal class CreateViewModel @Inject constructor(
    @IODispatcher dispatcher: CoroutineDispatcher,
    private val repository: AlbumRepository
) : UiStateViewModel<CreateAlbumState>(CreateAlbumState(), dispatcher) {

    fun handleIntent(intent: CreateAlbumIntent) {
        when (intent) {
            is CreateAlbumIntent.UpdateName -> updateName(intent.value)
            is CreateAlbumIntent.UpdateDescription -> updateDescription(intent.value)
            CreateAlbumIntent.CreateAlbum -> createAlbum()
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
            repository.createAlbum(
                data = CreateAlbumDTO(
                    name = stateData.name,
                    description = stateData.description
                )
            )
        }
    }

}
