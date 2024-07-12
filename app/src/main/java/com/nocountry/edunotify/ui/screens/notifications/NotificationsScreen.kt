package com.nocountry.edunotify.ui.screens.notifications

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.nocountry.edunotify.R
import com.nocountry.edunotify.ui.components.BottomNavigationBar
import com.nocountry.edunotify.ui.components.CircleButtonComponent
import com.nocountry.edunotify.ui.components.SpacerComponent
import com.nocountry.edunotify.ui.components.TopAppBarComponent
import com.nocountry.edunotify.ui.theme.EduNotifyTheme


//Mock data
data class Notification(
    val id: Int,
    val title: String,
    val message: String,
    val expiration: Int
)

val notifications = listOf(
    Notification(
        id = 1,
        title = "Title 1",
        message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
        expiration = 1
    ),
    Notification(id = 2, title = "Title 2", message = "Message2", expiration = 1),
    Notification(id = 3, title = "Title 3", message = "Message3", expiration = 1),
    Notification(id = 4, title = "Title 4", message = "Message4", expiration = 1),
)

@Composable
fun NotificationsScreen(
    onPlusClicked: () -> Unit,
    onNotificationClicked: (Int) -> Unit,
    notifications: List<Notification>,
    navController: NavHostController
) {
    val message by rememberSaveable { mutableStateOf(notifications[0].message) }

    Scaffold(
        topBar = {
            TopAppBarComponent(
                title = R.string.app_name,
                navigationIcon = { },
                actions = { },
            )
        },
        bottomBar = { BottomNavigationBar(navController) },
        floatingActionButton = { AddNewCourse(onPlusClicked = onPlusClicked) }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
        ) {
            if (message.isNotEmpty()) {
                CoursesCardList(
                    notifications = notifications,
                    onNotificationClicked = onNotificationClicked
                )
            } else {
                CourseEmptyList(
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun CoursesCardList(notifications: List<Notification>, onNotificationClicked: (Int) -> Unit) {
    LazyColumn {
        items(notifications) { notification ->
            SpacerComponent(height = 5.dp)
            CourseCard(
                notification = notification,
                modifier = Modifier.padding(10.dp),
                onNotificationClicked = onNotificationClicked
            )
        }
    }
}

@Composable
fun CourseCard(
    notification: Notification,
    onNotificationClicked: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .shadow(4.dp, shape = RoundedCornerShape(8.dp))
            .clickable { onNotificationClicked(notification.id) },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        border = BorderStroke(3.dp, MaterialTheme.colorScheme.inversePrimary),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = notification.title,
            style = MaterialTheme.typography.bodyLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, top = 10.dp, bottom = 5.dp)
        )
        Text(
            text = "Expira en ${notification.expiration} semana",
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 10.dp, bottom = 10.dp)
        )
    }
}

@Composable
fun CourseEmptyList(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.no_courses),
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .size(300.dp)
        )
        SpacerComponent(height = 10.dp)
        Text(
            text = stringResource(id = R.string.no_notifications_title),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = stringResource(id = R.string.no_notifications_message),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun AddNewCourse(onPlusClicked: () -> Unit, modifier: Modifier = Modifier) {
    Row(modifier = modifier.padding(bottom = 25.dp)) {
        SpacerComponent(modifier = Modifier.weight(1f))
        CircleButtonComponent(
            onClick = { onPlusClicked() },
            icon = R.drawable.plus,
            size = 60.dp
        )
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    EduNotifyTheme {
        NotificationsScreen(
            onPlusClicked = {},
            onNotificationClicked = {},
            notifications,
            navController = rememberNavController()
        )
    }
}