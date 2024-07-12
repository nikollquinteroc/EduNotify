package com.nocountry.edunotify.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nocountry.edunotify.R
import com.nocountry.edunotify.ui.theme.EduNotifyTheme

@Composable
fun TextFieldComponent(
    title: Int,
    value: String,
    onValueChange: (String) -> Unit,
    label: Int,
    leadingIcon: ImageVector?,
    trailingIcon: @Composable () -> Unit,
    keyboardOptions: KeyboardOptions,
    visualTransformation: VisualTransformation,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    readOnly: Boolean = false,
) {
    Column {
        Text(text = stringResource(id = title), style = MaterialTheme.typography.bodySmall)
        SpacerComponent(height = 5.dp)
        OutlinedTextField(
            modifier = Modifier.width(350.dp),
            value = value,
            onValueChange = onValueChange,
            label = {
                Text(
                    text = stringResource(label),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF7C7986)
                )
            },
            leadingIcon = {
                if (leadingIcon != null) {
                    Icon(
                        imageVector = leadingIcon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            },
            trailingIcon = { trailingIcon() },
            keyboardOptions = keyboardOptions,
            visualTransformation = visualTransformation,
            textStyle = MaterialTheme.typography.bodyMedium,
            supportingText = supportingText,
            isError = isError,
            readOnly = readOnly
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TextFieldComponentPreview() {
    EduNotifyTheme {
        TextFieldComponent(
            title = R.string.mail,
            value = "",
            onValueChange = { /*TODO*/ },
            label = R.string.mail_label,
            leadingIcon = Icons.Default.Email,
            trailingIcon = { },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            visualTransformation = VisualTransformation.None
        )
    }
}