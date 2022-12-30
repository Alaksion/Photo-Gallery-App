package br.com.alaksion.features.albums.presentation.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.LocationOn
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import platform.uicomponents.MviSampleSizes
import platform.uicomponents.components.spacers.VerticalSpacer
import platform.uicomponents.components.spacers.WeightSpacer
import platform.uistate.uistate.UiStateContent

internal object NameScreen : Screen {

    @Composable
    override fun Content() {
        val model = getViewModel<CreateViewModel>()
        val state by model.collectUiState()

        state.UiStateContent(
            stateContent = {
                StateContent(
                    name = it.name,
                    onNameChange = model::handleIntent
                )
            },
            errorState = {}
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    internal fun StateContent(
        name: String,
        onNameChange: (CreateAlbumIntent) -> Unit
    ) {
        val navigator = LocalNavigator.current
        val focus = LocalFocusManager.current
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Create a new album") },
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
                    text = "How is this album named?",
                    style = MaterialTheme.typography.headlineSmall,
                )
                VerticalSpacer(height = MviSampleSizes.xLarge)
                OutlinedTextField(
                    value = name,
                    onValueChange = { onNameChange(CreateAlbumIntent.UpdateName(it)) },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Grandma's cabin in the woods") },
                    label = { Text("Album's name") },
                    keyboardActions = KeyboardActions(
                        onDone = { focus.clearFocus() }
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    leadingIcon = {
                        Icon(imageVector = Icons.Outlined.LocationOn, contentDescription = null)
                    },
                )
                WeightSpacer(weight = 1f)
                Button(
                    onClick = { navigator?.push(DescriptionScreen) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = name.isNotEmpty()
                ) {
                    Text("Continue")
                }
            }
        }
    }

}