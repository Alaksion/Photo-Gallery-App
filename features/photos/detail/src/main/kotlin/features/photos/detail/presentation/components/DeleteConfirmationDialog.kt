package features.photos.detail.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import platform.uicomponents.MviBottomSheet
import platform.uicomponents.MviSampleSizes
import platform.uicomponents.components.buttongroup.ButtonGroupAxis
import platform.uicomponents.components.buttongroup.ButtonGroupItem
import platform.uicomponents.components.buttongroup.ButtonGroupItemType
import platform.uicomponents.components.buttongroup.MviButtonGroup
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
        MviButtonGroup(
            axis = ButtonGroupAxis.Horizontal,
            primaryButton = ButtonGroupItem(
                text = "Cancel",
                onClick = onClickCancel,
                type = ButtonGroupItemType.Outlined
            ),
            secondaryButton = ButtonGroupItem(
                text = "Delete this photo",
                onClick = onClickConfirm,
                type = ButtonGroupItemType.Regular
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}
