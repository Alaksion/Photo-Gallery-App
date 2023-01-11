package features.photos.detail.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import platform.uicomponents.MviSampleSizes
import platform.uicomponents.components.spacers.VerticalSpacer

@Composable
internal fun DeleteConfirmationDialog(
    onClickCancel: () -> Unit,
    onClickConfirm: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(all = MviSampleSizes.medium)
    ) {
        Box(
            modifier = Modifier
                .clip(MaterialTheme.shapes.large)
                .width(MviSampleSizes.xLarge3)
                .height(MviSampleSizes.xSmall2)
                .background(Color.LightGray)
                .align(CenterHorizontally)
        )
        VerticalSpacer(height = MviSampleSizes.medium)
        Text(
            text = "Deleting photo",
            style = MaterialTheme.typography.headlineSmall
        )
        VerticalSpacer(height = MviSampleSizes.medium)
        Text(
            text = "This photo will be permanently removed from this album. But you can add it back" +
                    "since this action will not delete the photo from your gallery."
        )
        VerticalSpacer(height = MviSampleSizes.medium)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MviSampleSizes.medium)
        ) {
            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = onClickCancel
            ) {
                Text("Cancel")
            }
            Button(
                modifier = Modifier.weight(1f),
                onClick = onClickConfirm
            ) {
                Text("Delete this photo")
            }
        }
    }
}