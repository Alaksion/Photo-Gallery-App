package com.example.uicomponents.spacers

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun VerticalSpacer(height: Dp) {
    Spacer(Modifier.height(height))
}

@Composable
fun HorizontalSpacer(width: Dp) {
    Spacer(Modifier.width(width))
}

@Composable
fun ColumnScope.WeightSpacer(weight: Float) {
    Spacer(Modifier.weight(weight))
}

@Composable
fun RowScope.WeightSpacer(weight: Float) {
    Spacer(Modifier.weight(weight))
}