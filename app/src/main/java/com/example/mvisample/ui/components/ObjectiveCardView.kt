package com.example.mvisample.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

internal data class ObjectiveCard(
    val title: String, val subtitle: String, val icon: ImageVector, val progress: Int
)

internal sealed class ObjectiveCardActions {
    data class OnClick(val id: String) : ObjectiveCardActions()
    data class Delete(val id: String) : ObjectiveCardActions()
}

@Composable
internal fun ObjectiveCardView(
    modifier: Modifier = Modifier,
    data: ObjectiveCard,
    actions: (ObjectiveCardActions) -> Unit
) {
    Card(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ObjectiveCardIcon(
                icon = data.icon, progress = data.progress
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = data.title,
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )
                )
                Text(
                    text = data.title,
                    style = TextStyle(
                        fontSize = 14.sp
                    )
                )
            }
            ObjectiveCardActions(actions = actions)
        }
    }
}

@Composable
private fun ObjectiveCardActions(
    actions: (ObjectiveCardActions) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        IconButton(onClick = { actions(ObjectiveCardActions.Delete("")) }) {
            Icon(imageVector = Icons.Outlined.Delete, contentDescription = null)
        }
    }
}

@Composable
private fun ObjectiveCardIcon(
    icon: ImageVector, progress: Int
) {
    if (progress == 100) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.onBackground,
                    CircleShape
                )
                .padding(3.dp),
            tint = MaterialTheme.colorScheme.background
        )
    } else {
        Box(contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                progress = progress.toFloat() / 100
            )
            Text(
                text = "${progress}%",
                style = TextStyle(fontWeight = FontWeight.Light, fontSize = 14.sp),
            )
        }

    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun Preview() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        ObjectiveCardView(
            data = ObjectiveCard(
                title = "Title",
                subtitle = "Subtitle",
                icon = Icons.Default.Add,
                progress = 90
            ), actions = {})
    }

}