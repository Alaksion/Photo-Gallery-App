package platform.uicomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import platform.uicomponents.components.spacers.VerticalSpacer

@Composable
fun MviBottomSheet(
    title: String,
    modifier: Modifier = Modifier,
    showPill: Boolean = true,
    contentPadding: PaddingValues = PaddingValues(MviSampleSizes.medium),
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier.padding(contentPadding)
    ) {
        if (showPill) {
            Box(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.large)
                    .width(MviSampleSizes.xLarge3)
                    .height(MviSampleSizes.xSmall2)
                    .background(Color.LightGray)
                    .align(Alignment.CenterHorizontally)
            )
            VerticalSpacer(height = MviSampleSizes.medium)
        }
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall
        )
        VerticalSpacer(height = MviSampleSizes.medium)
        content()
    }
}
