package features.photos.detail.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import platform.uicomponents.MviBottomSheet
import platform.uicomponents.MviSampleSizes
import platform.uicomponents.components.spacers.VerticalSpacer

@Composable
internal fun DeleteConfirmationDialog(
    onClickCancel: () -> Unit,
    onClickConfirm: () -> Unit,
    modifier: Modifier = Modifier,
) {
    MviBottomSheet(
        title = "Deleting photo",
        modifier = modifier,
    ) {
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
