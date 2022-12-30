package br.com.alaksion.features.albums.presentation.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
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


}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
private fun CreateAlbumContent(
    state: CreateAlbumState,
    handleIntent: (CreateAlbumIntent) -> Unit,
    goBack: () -> Unit,
) {
    val (nameFocus, descriptionFocus) = FocusRequester.createRefs()
    val focusRequester = LocalFocusManager.current
    val keyboard = LocalSoftwareKeyboardController.current

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
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(nameFocus),
                value = state.name,
                onValueChange = { name -> handleIntent(CreateAlbumIntent.UpdateName(name)) },
                label = { Text("Album's name") },
                placeholder = { Text("Grandma's field house") },
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = null,
                        tint = if (state.name.isEmpty()) LocalContentColor.current
                        else MaterialTheme.colorScheme.primary
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { descriptionFocus.requestFocus() }
                )
            )
            VerticalSpacer(height = MviSampleSizes.medium)
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(descriptionFocus),
                value = state.description,
                onValueChange = { description ->
                    handleIntent(
                        CreateAlbumIntent.UpdateDescription(
                            description
                        )
                    )
                },
                placeholder = { Text("A very nice summer vacation") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Info,
                        contentDescription = null,
                        tint = if (state.name.isEmpty()) LocalContentColor.current
                        else MaterialTheme.colorScheme.primary
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusRequester.clearFocus(true)
                        keyboard?.hide()
                    }
                ),
                label = { Text("Album's description") }
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
