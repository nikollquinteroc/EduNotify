package com.nocountry.edunotify.ui.screens.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nocountry.edunotify.R
import com.nocountry.edunotify.domain.model.NotificationDomain
import com.nocountry.edunotify.ui.components.CircleButtonComponent
import com.nocountry.edunotify.ui.components.SpacerComponent
import com.nocountry.edunotify.ui.components.TopAppBarComponent
import com.nocountry.edunotify.ui.theme.EduNotifyTheme

val fakeNotificationDomain = NotificationDomain(
    messageId = 0,
    messageDate = emptyList(),
    author = "Fake author",
    title = "Fake title",
    message = "Fake message",
    expiration = 0
)

@Composable
fun NotificationDetailScreen(
    notificationDomain: NotificationDomain,
    onBackClicked: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBarComponent(
                title = R.string.app_name,
                navigationIcon = {
                    CircleButtonComponent(
                        onClick = { onBackClicked() },
                        icon = R.drawable.arrow_back
                    )
                },
                actions = {}
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .shadow(4.dp, shape = RoundedCornerShape(8.dp)),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    border = BorderStroke(3.dp, MaterialTheme.colorScheme.inversePrimary),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = notificationDomain.title,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, top = 10.dp, bottom = 5.dp)
                    )
                    Text(
                        text = notificationDomain.message,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Justify,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    )
                    SpacerComponent(height = 5.dp)
                    Text(
                        text = "Expira en ${notificationDomain.expiration} semana",
                        textAlign = TextAlign.End,
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun DetailScreenPreview() {
    EduNotifyTheme {
        NotificationDetailScreen(
            onBackClicked = {},
            notificationDomain = fakeNotificationDomain
        )
    }
}