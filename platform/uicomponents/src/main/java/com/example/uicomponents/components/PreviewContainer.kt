package com.example.uicomponents.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.uicomponents.MviSampleSizes

@Composable
fun PreviewContainer(
    content: @Composable ColumnScope.() -> Unit
) {
    Surface() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(MviSampleSizes.medium)
        ) {
            content()
        }
    }
}
