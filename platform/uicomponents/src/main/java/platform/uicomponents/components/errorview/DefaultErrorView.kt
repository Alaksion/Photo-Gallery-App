package platform.uicomponents.components.errorview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.example.error.InternalException
import platform.uicomponents.MviSampleSizes
import platform.uicomponents.components.PreviewContainer
import platform.uicomponents.components.spacers.VerticalSpacer
import platform.uicomponents.components.spacers.WeightSpacer

@Composable
fun DefaultErrorView(
    error: Throwable,
    options: DefaultErrorViewOptions,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        WeightSpacer(weight = 1f)
        Icon(
            imageVector = Icons.Rounded.Warning,
            contentDescription = null,
            modifier = Modifier
                .size(MviSampleSizes.xxLarge3)
                .align(Alignment.CenterHorizontally),
            tint = MaterialTheme.colorScheme.primary
        )
        VerticalSpacer(height = MviSampleSizes.small)
        Text(
            text = "An error Occurred",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold
        )
        VerticalSpacer(height = MviSampleSizes.xSmall2)
        Text(
            text = when (error) {
                is InternalException.Generic -> error.text
                else -> "Looks like something is wrong, try again later"
            },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.bodyMedium,
        )
        WeightSpacer(weight = 1f)
        VerticalSpacer(height = MviSampleSizes.medium)
        Column(
            Modifier.fillMaxWidth()
        ) {
            options.primaryButton?.let { primaryButton ->
                Button(
                    onClick = primaryButton.onClick,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = primaryButton.enabled
                ) {
                    Text(text = primaryButton.title)
                }
            }
            options.secondaryButton?.let { secondaryButton ->
                OutlinedButton(
                    onClick = secondaryButton.onClick,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = secondaryButton.enabled
                ) {
                    Text(text = secondaryButton.title)
                }
            }
        }

    }
}

@Suppress("ThrowingExceptionsWithoutMessageOrCause")
@Preview(showBackground = true)
@Composable
private fun Preview(
    @PreviewParameter(DefaultErrorViewOptionsProvider::class) options: DefaultErrorViewOptions
) {
    PreviewContainer {
        DefaultErrorView(
            error = IllegalArgumentException(),
            options = options
        )
    }
}
