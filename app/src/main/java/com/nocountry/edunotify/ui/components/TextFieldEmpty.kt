package com.nocountry.edunotify.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.nocountry.edunotify.R

@Composable
fun TextFieldEmpty() {
    Text(
        text = stringResource(id = R.string.error_label),
        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Normal)
    )
}