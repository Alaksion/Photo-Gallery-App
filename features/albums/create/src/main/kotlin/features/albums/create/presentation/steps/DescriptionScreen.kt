package features.albums.create.presentation.steps

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import features.albums.create.presentation.CreateAlbumIntent
import features.albums.create.presentation.CreateViewModel
import features.albums.create.presentation.extensions.toolbarTitle
import platform.navigation.params.ManageAlbumOperation
import platform.uicomponents.MviSampleSizes
import platform.uicomponents.components.spacers.VerticalSpacer
import platform.uicomponents.components.spacers.WeightSpacer
import platform.uicomponents.extensions.UiStateContent

internal data class DescriptionScreen(
    private val type: ManageAlbumOperation
) : Screen {

    @Composable
    override fun Content() {
        val model = getViewModel<CreateViewModel>()
        val state by model.state.collectAsState()

        state.UiStateContent(
            stateContent = {
                StateContent(
                    description = it.album.description,
                    onNameChange = model::handleIntent
                )
            },
            errorState = {}
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    internal fun StateContent(
        description: String,
        onNameChange: (CreateAlbumIntent) -> Unit
    ) {
        val navigator = LocalNavigator.current
        val focus = LocalFocusManager.current
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(type.toolbarTitle()) },
                    navigationIcon = {
                        IconButton(onClick = { navigator?.pop() }) {
                            Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = null)
                        }
                    }
                )
            },
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .padding(MviSampleSizes.medium)
            ) {
                Text(
                    text = "Tell us how this place is special to you",
                    style = MaterialTheme.typography.headlineSmall,
                )
                VerticalSpacer(height = MviSampleSizes.xLarge)
                OutlinedTextField(
                    value = description,
                    onValueChange = { onNameChange(CreateAlbumIntent.UpdateDescription(it)) },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Album's description") },
                    keyboardActions = KeyboardActions(
                        onDone = { focus.clearFocus() }
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    leadingIcon = {
                        Icon(imageVector = Icons.Outlined.List, contentDescription = null)
                    },
                    maxLines = 20
                )
                WeightSpacer(weight = 1f)
                Button(
                    onClick = { navigator?.push(CreateAlbumScreen(type)) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = description.isNotEmpty()
                ) {
                    Text("Continue")
                }
            }
        }
    }

}
