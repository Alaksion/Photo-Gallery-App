package features.photos.presentation.gallerypick

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.core.screen.Screen
import coil.compose.AsyncImage
import platform.uicomponents.MviSampleSizes
import platform.uicomponents.components.PreviewContainer
import platform.uicomponents.components.spacers.HorizontalSpacer

internal data class GalleryPhotoPickerScreen(
    private val albumInt: Int
) : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {

        var uris by remember {
            mutableStateOf(listOf<Uri>())
        }

        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickMultipleVisualMedia(20),
            onResult = { imageUri ->
                imageUri.let {
                    uris = uris + it
                }
            }
        )

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Pick Photos from gallery") },
                    navigationIcon = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(imageVector = Icons.Outlined.ArrowBack, null)
                        }
                    }
                )
            },
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(horizontal = MviSampleSizes.medium)
            ) {
                Button(
                    onClick = {
                        launcher.launch(
                            PickVisualMediaRequest(
                                mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    }
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Open Gallery")
                        HorizontalSpacer(width = MviSampleSizes.xSmall3)
                        Icon(imageVector = Icons.Outlined.Add, null)
                    }
                }
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3)
                ) {
                    items(uris) { Uri ->
                        AsyncImage(
                            model = Uri,
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    PreviewContainer {
        GalleryPhotoPickerScreen(1).Content()
    }
}

