package features.albums.create.presentation.steps

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import features.albums.create.presentation.CreateAlbumIntent
import features.albums.create.presentation.CreateViewModel
import features.albums.create.presentation.ManageAlbumState
import features.albums.create.presentation.extensions.toolbarTitle
import platform.navigation.params.ManageAlbumOperation
import platform.uicomponents.MviSampleSizes
import platform.uicomponents.components.errorview.DefaultErrorView
import platform.uicomponents.components.errorview.DefaultErrorViewOptions
import platform.uicomponents.components.spacers.VerticalSpacer
import platform.uicomponents.components.spacers.WeightSpacer
import platform.uicomponents.extensions.UiStateContent
import platform.uicomponents.sideeffects.UiEventEffect

internal data class CreateAlbumScreen(
    private val type: ManageAlbumOperation
) : Screen {

    @Composable
    override fun Content() {
        val model = getViewModel<CreateViewModel>()
        val state by model.state.collectAsState()
        val navigator = LocalNavigator.current

        UiEventEffect(eventHandler = model) {
            navigator?.push(AlbumResultScreen(it.result, type))
        }

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

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun CreateAlbumContent(
        state: ManageAlbumState,
        handleIntent: (CreateAlbumIntent) -> Unit,
        goBack: () -> Unit,
    ) {

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(type.toolbarTitle()) },
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
                    description = state.album.name,
                    icon = Icons.Outlined.LocationOn,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = MviSampleSizes.medium)
                )
                ConfirmationCard(
                    label = "Album's description",
                    description = state.album.description,
                    icon = Icons.Outlined.List,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = MviSampleSizes.medium)
                )
                WeightSpacer(weight = 1f)
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { handleIntent(CreateAlbumIntent.SubmitAlbum(type)) },
                    enabled = state.isButtonEnabled
                ) {
                    Text("Create Album")
                }
            }

        }
    }

}
