package platform.uicomponents.components.buttongroup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import platform.uicomponents.MviSampleSizes
import platform.uicomponents.components.PreviewContainer
import platform.uicomponents.components.spacers.HorizontalSpacer
import platform.uicomponents.components.spacers.VerticalSpacer

/**
 * Component designed to host 2 buttons inside a container.
 *
 * @param modifier Compose modifier to customize this component if needed
 * @param axis Direction of this button group. Can be [ButtonGroupAxis.Horizontal] or
 * [ButtonGroupAxis.Vertical]
 * @param primaryButton Primary button of this button group. For axis configuration
 * [ButtonGroupAxis.Vertical] primary Button stays on top, for axis configuration
 * [ButtonGroupAxis.Horizontal] this parameter configures the left button.
 * @param secondaryButton Secondary button of this button group, this parameter is optional.
 * If not specified the button group will contain only one button. If axis is [ButtonGroupAxis.Vertical]
 * only one row will be rendered, and if the axis is [ButtonGroupAxis.Horizontal] a single button
 * filling the whole width of the component will be rendered.
 * */
@Composable
fun MviButtonGroup(
    modifier: Modifier = Modifier,
    axis: ButtonGroupAxis,
    primaryButton: ButtonGroupItem,
    secondaryButton: ButtonGroupItem? = null,
    contentPadding: PaddingValues = PaddingValues(),
) {
    if (axis == ButtonGroupAxis.Horizontal) {
        HorizontalButtonGroup(
            modifier = modifier
                .padding(contentPadding)
                .fillMaxWidth(),
            primaryButton = primaryButton,
            secondaryButton = secondaryButton
        )
    } else {
        VerticalButtonGroup(
            primaryButton = primaryButton,
            secondaryButton = secondaryButton,
            modifier = modifier
                .padding(contentPadding)
                .fillMaxWidth()
        )
    }
}

@Composable
private fun VerticalButtonGroup(
    modifier: Modifier = Modifier,
    primaryButton: ButtonGroupItem,
    secondaryButton: ButtonGroupItem?
) {
    Column(
        modifier = modifier,
    ) {
        MviButton(
            data = primaryButton,
            modifier = Modifier.fillMaxWidth()
        )
        secondaryButton?.let { button ->
            VerticalSpacer(height = MviSampleSizes.medium)
            MviButton(
                data = button,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun HorizontalButtonGroup(
    modifier: Modifier = Modifier,
    primaryButton: ButtonGroupItem,
    secondaryButton: ButtonGroupItem?
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        MviButton(
            data = primaryButton,
            modifier = Modifier.weight(1f)
        )
        secondaryButton?.let { button ->
            HorizontalSpacer(width = MviSampleSizes.medium)
            MviButton(
                data = button,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun MviButton(
    data: ButtonGroupItem,
    modifier: Modifier = Modifier
) {
    when (data.type) {
        ButtonGroupItemType.Outlined -> {
            OutlinedButton(
                modifier = modifier,
                enabled = data.enabled,
                onClick = data.onClick
            ) {
                Text(data.text)
            }
        }

        ButtonGroupItemType.Regular -> {
            Button(
                onClick = data.onClick,
                modifier = modifier,
            ) {
                Text(data.text)
            }
        }

        ButtonGroupItemType.Text -> {
            TextButton(
                onClick = data.onClick,
                modifier = modifier
            ) {
                Text(data.text)
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun Preview() {

    PreviewContainer {
        val scroll = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scroll),
            verticalArrangement = Arrangement.spacedBy(MviSampleSizes.medium)
        ) {
            MviButtonGroup(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray),
                axis = ButtonGroupAxis.Horizontal,
                primaryButton = ButtonGroupItem(
                    text = "Cancel",
                    onClick = {},
                    type = ButtonGroupItemType.Outlined
                ),
                secondaryButton = ButtonGroupItem(
                    text = "Delete",
                    onClick = { },
                    type = ButtonGroupItemType.Regular
                )
            )
            MviButtonGroup(
                modifier = Modifier.fillMaxWidth(),
                axis = ButtonGroupAxis.Horizontal,
                primaryButton = ButtonGroupItem(
                    text = "Cancel",
                    onClick = {},
                    type = ButtonGroupItemType.Outlined
                ),
            )
            MviButtonGroup(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray),
                axis = ButtonGroupAxis.Vertical,
                primaryButton = ButtonGroupItem(
                    text = "Cancel",
                    onClick = {},
                    type = ButtonGroupItemType.Outlined
                ),
                secondaryButton = ButtonGroupItem(
                    text = "Delete",
                    onClick = { },
                    type = ButtonGroupItemType.Regular
                )
            )
            MviButtonGroup(
                modifier = Modifier.fillMaxWidth(),
                axis = ButtonGroupAxis.Vertical,
                primaryButton = ButtonGroupItem(
                    text = "Cancel",
                    onClick = {},
                    type = ButtonGroupItemType.Outlined
                ),
            )
        }
    }

}
