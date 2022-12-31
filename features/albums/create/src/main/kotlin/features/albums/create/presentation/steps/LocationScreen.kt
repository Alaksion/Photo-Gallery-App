package features.albums.create.presentation.steps

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.lifecycle.ScreenLifecycleProvider
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import features.albums.create.presentation.CreateAlbumFlowProvider

internal data class Location(
    val latitude: Float,
    val longitude: Float
)

internal object LocationScreen : Screen, ScreenLifecycleProvider by CreateAlbumFlowProvider() {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        StateContent(
            goBack = { navigator?.pop() }
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    internal fun StateContent(
        goBack: () -> Unit
    ) {
        val mapState = rememberCameraPositionState()
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
            Column(modifier = Modifier.padding(it)) {
                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = mapState
                )
            }
        }
    }

}
