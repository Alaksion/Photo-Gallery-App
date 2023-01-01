package features.albums.details

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import features.albums.details.presentation.AlbumDetailsScreen
import platform.uicomponents.MviSampleSizes
import platform.uicomponents.components.PreviewContainer

@Preview(showBackground = true)
@Composable
private fun Preview() {
    PreviewContainer(contentPaddingValues = PaddingValues(vertical = MviSampleSizes.medium)) {
        AlbumDetailsScreen(1).DetailScreen()
    }
}
