package br.com.alaksion.features.albums.presentation.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import platform.uicomponents.components.errorview.DefaultErrorView
import platform.uicomponents.components.errorview.DefaultErrorViewOptions
import platform.uistate.uistate.UiStateContent

internal object CreateAlbumScreen : AndroidScreen() {

    @Composable
    override fun Content() {
        val model = getViewModel<CreateViewModel>()
        val state by model.collectUiState()

        state.UiStateContent(
            stateContent = {
                CreateAlbumContent(
                    state = it,
                    handleIntent = model::handleIntent
                )
            },
            errorState = {
                DefaultErrorView(
                    error = it,
                    options = DefaultErrorViewOptions(
                        primaryButton = null,
                        secondaryButton = null
                    )
                )
            }
        )
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CreateAlbumContent(
    state: CreateAlbumState,
    handleIntent: (CreateAlbumIntent) -> Unit
) {
    Scaffold() {
        Column(Modifier.padding(it)) {
            Button(
                onClick = { handleIntent(CreateAlbumIntent.CreateAlbum) }
            ) {
                Text("adawd")
            }
        }

    }
}
