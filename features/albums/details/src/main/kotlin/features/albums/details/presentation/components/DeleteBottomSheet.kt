package features.albums.details.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import platform.uicomponents.MviBottomSheet
import platform.uicomponents.MviSampleSizes
import platform.uicomponents.components.PreviewContainer
import platform.uicomponents.components.buttongroup.ButtonGroupAxis
import platform.uicomponents.components.buttongroup.ButtonGroupItem
import platform.uicomponents.components.buttongroup.ButtonGroupItemType
import platform.uicomponents.components.buttongroup.MviButtonGroup
import platform.uicomponents.components.spacers.VerticalSpacer

@Composable
internal fun DeleteBottomSheet(
    modifier: Modifier = Modifier,
    onCancel: () -> Unit,
    onContinue: () -> Unit
) {
    MviBottomSheet(
        title = "Delete album",
        modifier = modifier
    ) {
        Text(
            "This album will be permanently deleted, are you sure you want to continue?" +
                    "\n\nPhotos attached to this album won't be deleted from your gallery."
        )
        VerticalSpacer(height = MviSampleSizes.large)
        MviButtonGroup(
            modifier = Modifier.fillMaxWidth(),
            axis = ButtonGroupAxis.Horizontal,
            primaryButton = ButtonGroupItem(
                text = "Cancel",
                onClick = onCancel,
                type = ButtonGroupItemType.Outlined
            ),
            secondaryButton = ButtonGroupItem(
                text = "Delete",
                onClick = onContinue,
                type = ButtonGroupItemType.Regular
            )
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun Preview() {
    PreviewContainer() {
        DeleteBottomSheet(
            modifier = Modifier.fillMaxWidth(),
            onCancel = { /*TODO*/ }
        ) {

        }
    }
}