package com.nocountry.edunotify.ui.screens.notifications

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.nocountry.edunotify.ui.theme.EduNotifyTheme

@Composable
fun NotificationsScreen(onPlusClicked: () -> Unit, onNotificationClicked: (Int) -> Unit) {
    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Notifications")
            Card(modifier = Modifier.clickable { onNotificationClicked(1) }) {
                Text(text = "message1")
            }
            Button(onClick = onPlusClicked) {
                Text(text = "plus")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotificationsScreenPreview() {
    EduNotifyTheme {
        NotificationsScreen(onPlusClicked = {}, onNotificationClicked = {})
    }
}