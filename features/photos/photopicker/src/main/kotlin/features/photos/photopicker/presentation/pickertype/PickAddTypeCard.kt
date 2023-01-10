package features.photos.photopicker.presentation.pickertype

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import platform.uicomponents.MviSampleSizes
import platform.uicomponents.components.PreviewContainer

@Composable
internal fun PickAddTypeCard(
    title: String,
    description: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(vertical = MviSampleSizes.medium),
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .padding(contentPadding)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MviSampleSizes.medium)
    ) {
        Icon(
            imageVector = icon,
            null
        )
        Column(Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = description
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun Preview() {
    PreviewContainer() {
        PickAddTypeCard(
            title = "Some title",
            description = "some desc.",
            icon = Icons.Default.Person,
            onClick = {}
        )
    }
}
