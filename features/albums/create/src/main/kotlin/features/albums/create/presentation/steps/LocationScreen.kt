package features.albums.create.presentation.steps

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.lifecycle.ScreenLifecycleProvider
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import features.albums.create.presentation.CreateAlbumFlowProvider
import features.albums.create.presentation.CreateAlbumIntent
import features.albums.create.presentation.CreateAlbumState
import features.albums.create.presentation.CreateViewModel
import platform.uistate.uistate.UiStateContent

internal object LocationScreen : Screen, ScreenLifecycleProvider by CreateAlbumFlowProvider() {

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
                    state = stateData
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
        goBack: () -> Unit
    ) {

        val location = remember { mutableStateOf(LatLng(-30.033056, -51.230000)) }

        val cameraState = rememberCameraPositionState() {
            position = CameraPosition.fromLatLngZoom(
                location.value, 20f
            )
        }

        Scaffold() {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                GoogleMap(
                    cameraPositionState = cameraState,
                    onMapClick = { coordinates ->
                        location.value = coordinates
                    }
                ) {
                    Marker(
                        MarkerState(state.location)
                    )
                }
            }

        }
    }

}
