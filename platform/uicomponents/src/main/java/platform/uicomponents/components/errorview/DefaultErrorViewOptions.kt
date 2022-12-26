package platform.uicomponents.components.errorview

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.tooling.preview.PreviewParameterProvider

data class DefaultErrorViewButton(
    val title: String,
    val onClick: () -> Unit,
    val enabled: Boolean = true
)

data class DefaultErrorViewOptions(
    val primaryButton: DefaultErrorViewButton?,
    val secondaryButton: DefaultErrorViewButton?,
    val contentPadding: PaddingValues = PaddingValues()
)

internal class DefaultErrorViewOptionsProvider : PreviewParameterProvider<DefaultErrorViewOptions> {

    override val values: Sequence<DefaultErrorViewOptions>
        get() = sequenceOf(
            DefaultErrorViewOptions(
                primaryButton = DefaultErrorViewButton(
                    title = "Title",
                    enabled = true,
                    onClick = {}
                ),
                secondaryButton = DefaultErrorViewButton(
                    title = "Title Secondary",
                    enabled = true,
                    onClick = {}
                )
            ),
            DefaultErrorViewOptions(
                primaryButton = DefaultErrorViewButton(
                    title = "Title",
                    enabled = false,
                    onClick = {}
                ),
                secondaryButton = DefaultErrorViewButton(
                    title = "Title Secondary",
                    enabled = true,
                    onClick = {}
                )
            ),
            DefaultErrorViewOptions(
                primaryButton = DefaultErrorViewButton(
                    title = "Title",
                    enabled = true,
                    onClick = {}
                ),
                secondaryButton = DefaultErrorViewButton(
                    title = "Title Secondary",
                    enabled = false,
                    onClick = {}
                )
            ),
            DefaultErrorViewOptions(
                primaryButton = DefaultErrorViewButton(
                    title = "Title",
                    enabled = true,
                    onClick = {}
                ),
                secondaryButton = null
            ),
            DefaultErrorViewOptions(
                primaryButton = DefaultErrorViewButton(
                    title = "Title",
                    enabled = false,
                    onClick = {}
                ),
                secondaryButton = null
            ),
            DefaultErrorViewOptions(
                primaryButton = null,
                secondaryButton = DefaultErrorViewButton(
                    title = "Title",
                    enabled = true,
                    onClick = {}
                ),
            ),
            DefaultErrorViewOptions(
                primaryButton = null,
                secondaryButton = DefaultErrorViewButton(
                    title = "Title",
                    enabled = false,
                    onClick = {}
                ),
            )
        )

}
