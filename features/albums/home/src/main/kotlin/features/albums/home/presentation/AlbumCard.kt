package features.albums.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import platform.database.models.models.AlbumModel
import platform.uicomponents.MviSampleSizes
import platform.uicomponents.components.PreviewContainer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AlbumCard(
    data: AlbumModel,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        onClick = { onClick(data.id) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MviSampleSizes.small),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MviSampleSizes.medium)
        ) {
            Icon(
                imageVector = Icons.Outlined.Place,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(MviSampleSizes.xLarge)
            )
            Column(Modifier.weight(1f)) {
                Text(
                    text = data.name,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = data.description,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Icon(
                imageVector = Icons.Rounded.ArrowForward,
                contentDescription = null,
                modifier = Modifier.size(MviSampleSizes.xLarge)
            )

        }
    }
}

@Composable
@Preview(showBackground = true)
private fun Preview() {
    PreviewContainer {
        AlbumCard(
            data = AlbumModel.fixture,
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}
