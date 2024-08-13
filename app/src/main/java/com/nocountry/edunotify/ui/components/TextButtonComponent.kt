package com.nocountry.edunotify.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.nocountry.edunotify.ui.theme.EduNotifyTheme

@Composable
fun <T> TextButtonComponent(
    textLabel: String,
    options: List<T>,
    getDescription: (T) -> String,
    selectedOption: T?,
    onOptionSelected: (T) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    //In order to set the width DropDownMenuItem
    var buttonSize by remember { mutableStateOf(IntSize.Zero) }

    Box(
        modifier = modifier
            .width(350.dp)
            .onGloballyPositioned { coordinates ->
                buttonSize = coordinates.size
            }
            .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(4.dp))
            .height(60.dp)
    ) {
        TextButton(
            onClick = { expanded = true },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            TextLabelComponent(text = selectedOption?.let { getDescription(it) } ?: textLabel)
            Spacer(modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.Default.ExpandMore, contentDescription = null)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { buttonSize.width.toDp() })
                .heightIn(50.dp)
        ) {
            options.forEach { option ->
                val text = getDescription(option)
                DropdownMenuItem(
                    text = { Text(text = text) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun TextButtonComponentPreview() {
    EduNotifyTheme {
        TextButtonComponent(
            textLabel = "Test",
            options = listOf("Hello", "Test"),
            getDescription = { "Hello" },
            selectedOption = null,
            onOptionSelected = { "" })
    }
}