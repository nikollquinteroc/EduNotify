package com.nocountry.edunotify.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nocountry.edunotify.R
import com.nocountry.edunotify.ui.theme.EduNotifyTheme

@Composable
fun CircleButtonComponent(
    onClick: () -> Unit,
    icon: Int,
    modifier: Modifier = Modifier,
    size: Dp = 36.dp
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .size(size)
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = CircleShape
            )
    ) {
        Icon(
            painter = painterResource(id = icon),
            tint = MaterialTheme.colorScheme.onPrimary,
            contentDescription = null,
            modifier = Modifier
                .width(24.dp)
                .height(24.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ArrowBackComponentPreview() {
    EduNotifyTheme {
        CircleButtonComponent(onClick = {}, icon = R.drawable.arrow_back)
    }
}
