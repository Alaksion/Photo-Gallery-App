package br.com.alaksion.features.albums.presentation.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import platform.uicomponents.MviSampleSizes
import platform.uicomponents.components.errorview.DefaultErrorView
import platform.uicomponents.components.errorview.DefaultErrorViewOptions
import platform.uicomponents.components.spacers.VerticalSpacer
import platform.uicomponents.components.spacers.WeightSpacer
import platform.uistate.uistate.UiStateContent

internal object CreateAlbumScreen : AndroidScreen() {

    @Composable
    override fun Content() {
        val model = getViewModel<CreateViewModel>()
        val state by model.collectUiState()
        val navigator = LocalNavigator.current

        state.UiStateContent(
            stateContent = {
                CreateAlbumContent(
                    state = it,
                    handleIntent = model::handleIntent,
                    goBack = { navigator?.pop() }
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

    @Composable
    private fun ConfirmationCard(
        label: String,
        description: String,
        icon: ImageVector,
        modifier: Modifier = Modifier
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MviSampleSizes.medium)
        ) {
            Icon(imageVector = icon, contentDescription = null)
            Column(Modifier.fillMaxWidth()) {
                Text(
                    text = label,
                    fontWeight = FontWeight.SemiBold
                )
                Text(description)
            }
        }

    }

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
    @Composable
    private fun CreateAlbumContent(
        state: CreateAlbumState,
        handleIntent: (CreateAlbumIntent) -> Unit,
        goBack: () -> Unit,
    ) {

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Create New Album") },
                    navigationIcon = {
                        IconButton(onClick = goBack) {
                            Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = null)
                        }
                    }
                )
            }
        ) {
            Column(
                Modifier
                    .padding(it)
                    .padding(MviSampleSizes.medium)
            ) {
                VerticalSpacer(height = MviSampleSizes.medium)
                ConfirmationCard(
                    label = "Album's name",
                    description = state.name,
                    icon = Icons.Outlined.LocationOn,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = MviSampleSizes.medium)
                )
                ConfirmationCard(
                    label = "Album's description",
                    description = state.description,
                    icon = Icons.Outlined.List,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = MviSampleSizes.medium)
                )
                WeightSpacer(weight = 1f)
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { handleIntent(CreateAlbumIntent.CreateAlbum) },
                    enabled = state.isButtonEnabled
                ) {
                    Text("Create Album")
                }
            }

        }
    }

}
