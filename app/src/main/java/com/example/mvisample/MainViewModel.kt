package com.example.mvisample

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Warning
import androidx.lifecycle.ViewModel
import com.example.mvisample.ui.components.ObjectiveCard

internal class MainViewModel : ViewModel() {

    val objectives = listOf(
        ObjectiveCard(
            title = "Title 1",
            progress = 100,
            icon = Icons.Outlined.Notifications,
            subtitle = "Subtitle 1"
        ),
        ObjectiveCard(
            title = "Title 2",
            progress = 90,
            icon = Icons.Outlined.Warning,
            subtitle = "Subtitle 2"
        ),
        ObjectiveCard(
            title = "Title 1",
            progress = 100,
            icon = Icons.Outlined.PlayArrow,
            subtitle = "Subtitle 1"
        ),
        ObjectiveCard(
            title = "Title 1",
            progress = 23,
            icon = Icons.Outlined.Delete,
            subtitle = "Subtitle 1"
        )
    )

}