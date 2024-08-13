package com.nocountry.edunotify.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.nocountry.edunotify.ui.theme.EduNotifyTheme

@Composable
fun TextLabelComponent(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Normal),
        color = Color.Gray,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun TextLabelComponentPreview() {
    EduNotifyTheme {
        TextLabelComponent(text = "Test")
    }
}