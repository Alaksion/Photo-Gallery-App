package features.albums.create.presentation.steps

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
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
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import features.albums.create.presentation.CreateAlbumIntent
import features.albums.create.presentation.CreateAlbumState
import features.albums.create.presentation.CreateViewModel
import platform.uicomponents.MviSampleSizes
import platform.uistate.uistate.UiStateContent

private const val MapZoom = 20f

internal object LocationScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val model = getViewModel<CreateViewModel>()
        val state by model.uiState.collectAsState()

        state.UiStateContent(
            stateContent = { stateData ->
                StateContent(
                    goBack = { navigator?.pop() },
                    handleIntent = model::handleIntent,
                    state = stateData,
                    proceed = { navigator?.push(CreateAlbumScreen) }
                )
            },
            errorState = {}
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    internal fun StateContent(
        state: CreateAlbumState,
        handleIntent: (CreateAlbumIntent) -> Unit,
        goBack: () -> Unit,
        proceed: () -> Unit
    ) {


        val cameraState = rememberCameraPositionState() {
            position = CameraPosition.fromLatLngZoom(
                state.location, MapZoom
            )
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        IconButton(onClick = goBack) {
                            Icon(imageVector = Icons.Outlined.ArrowBack, null)
                        }
                    }
                )
            }
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                Button(
                    onClick = proceed,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(MviSampleSizes.medium)
                ) {
                    Text("Continue")
                }
                GoogleMap(
                    cameraPositionState = cameraState,
                    onMapClick = { coordinates ->
                        handleIntent(CreateAlbumIntent.UpdateLocation(coordinates))
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Marker(
                        MarkerState(state.location)
                    )
                }
            }
        }
    }

}
