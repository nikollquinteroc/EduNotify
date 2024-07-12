package com.nocountry.edunotify.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SpacerComponent(
    height: Dp = 0.dp,
    width: Dp = 0.dp,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    Spacer(
        modifier = modifier
            .height(height)
            .width(width)
    )
}