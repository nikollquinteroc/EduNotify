package com.nocountry.edunotify.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nocountry.edunotify.R
import com.nocountry.edunotify.ui.theme.EduNotifyTheme

@Composable
fun ButtonComponent(
    text: Int,
    onClick: () -> Unit,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isSelected) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        MaterialTheme.colorScheme.primary
    }
    var isPressed by rememberSaveable { mutableStateOf(false) }

    TextButton(
        onClick = {
            isPressed = true
            onClick()
        },
        modifier = modifier
            .width(350.dp)
            .height(52.dp)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(size = 8.dp)
            )
    ) {
        Text(
            text = stringResource(id = text),
            color = Color(0xFFFFFFFF),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ButtonComponentPreview() {
    EduNotifyTheme {
        ButtonComponent(text = R.string.login, onClick = {}, isSelected = false)
    }
}