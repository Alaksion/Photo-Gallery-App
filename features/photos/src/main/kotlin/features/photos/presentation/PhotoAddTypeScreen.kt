package features.photos.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.PhotoAlbum
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.core.screen.Screen
import platform.uicomponents.MviSampleSizes
import platform.uicomponents.components.PreviewContainer
import platform.uicomponents.components.spacers.VerticalSpacer

internal data class PhotoAddTypeScreen(
    private val albumId: Int
) : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Add photos") },
                    navigationIcon = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(imageVector = Icons.Outlined.ArrowBack, null)
                        }
                    }
                )
            }
        ) {
            Column(
                Modifier
                    .padding(it)
                    .padding(horizontal = MviSampleSizes.medium)
            ) {
                Text(
                    text = "From where do you want to pick the photos?",
                    style = MaterialTheme.typography.titleLarge
                )
                VerticalSpacer(height = MviSampleSizes.medium)
                PickAddTypeCard(
                    title = "Pick from gallery",
                    description = "Open your phone's gallery and select photos from it",
                    icon = Icons.Outlined.PhotoAlbum,
                    onClick = {}
                )
                Divider()
                PickAddTypeCard(
                    title = "Take a picture",
                    description = "Open your camera and take a picture",
                    icon = Icons.Outlined.PhotoCamera,
                    onClick = {}
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    PreviewContainer {
        PhotoAddTypeScreen(1).Content()
    }
}