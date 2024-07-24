package com.nocountry.edunotify.ui.screens.notifications

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
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
import com.nocountry.edunotify.domain.model.AuthDomain
import com.nocountry.edunotify.domain.model.CourseDomain
import com.nocountry.edunotify.domain.model.NotificationDomain
import com.nocountry.edunotify.domain.model.UserDomain
import com.nocountry.edunotify.ui.components.BottomNavigationBar
import com.nocountry.edunotify.ui.components.CircleButtonComponent
import com.nocountry.edunotify.ui.components.SpacerComponent
import com.nocountry.edunotify.ui.components.TopAppBarComponent
import com.nocountry.edunotify.ui.theme.EduNotifyTheme


//Mock data
data class NotificationMock(
    val id: Int,
    val title: String,
    val message: String,
    val expiration: Int
)

@Composable
fun NotificationsScreen(
    onPlusClicked: () -> Unit,
    onNotificationClicked: (Int) -> Unit,
    authDomain: AuthDomain,
    navController: NavHostController
) {
    val courses by rememberSaveable { mutableStateOf(authDomain.user?.courses) }

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
            if (courses.isNullOrEmpty().not()) {
                CoursesSectionList(
                    courses = courses,
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
fun CoursesSectionList(
    courses: List<CourseDomain>?,
    onNotificationClicked: (Int) -> Unit
) {
    LazyColumn {
        items(courses!!) { courseDomain ->
            CourseSection(
                courseDomain = courseDomain,
                onNotificationClicked = onNotificationClicked
            )
        }
    }
}

@Composable
fun CourseSection(courseDomain: CourseDomain, onNotificationClicked: (Int) -> Unit) {
    Column {
        Text(
            text = courseDomain.course,
            modifier = Modifier.padding(start = 15.dp, top = 10.dp)
        )
        CoursesCardList(notifications = courseDomain.notifications, onNotificationClicked)
    }
}

@Composable
fun CoursesCardList(notifications: List<NotificationDomain>, onNotificationClicked: (Int) -> Unit) {
    LazyRow {
        items(notifications) { notification ->
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
    notification: NotificationDomain,
    onNotificationClicked: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = modifier
            .shadow(4.dp, shape = RoundedCornerShape(8.dp))
            .clickable { onNotificationClicked(notification.messageId) }
            .width(200.dp)
            .height(200.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column {
            Box(modifier = Modifier.weight(1f)) {
                Image(
                    painter = painterResource(id = R.drawable.note_image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = notification.title,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Text(
                    text = "Expira en ${notification.expiration} semanas",
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 20.dp, bottom = 10.dp)
                )
            }
        }
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
            navController = rememberNavController(),
            authDomain = AuthDomain(
                jwt = "fsdg", user = UserDomain(
                    id = 1,
                    name = "Nikoll",
                    lastName = "Quintero",
                    email = "nikoll@gmail.com",
                    phone = "32456433",
                    role = "USUARIO",
                    school = 1,
                    courses = listOf(
                        CourseDomain(
                            course = "Sala Roja",
                            courseId = 1,
                            notifications = listOf(
                                NotificationDomain(
                                    messageId = 1,
                                    messageDate = emptyList(),
                                    author = "Carlos Morales",
                                    title = "Anuncio importante",
                                    message = "Mañana no hay clases",
                                    expiration = 2
                                ),
                                NotificationDomain(
                                    messageId = 1,
                                    messageDate = emptyList(),
                                    author = "Carlos Morales",
                                    title = "Anuncio importante",
                                    message = "Mañana no hay clases",
                                    expiration = 2
                                ),
                                NotificationDomain(
                                    messageId = 1,
                                    messageDate = emptyList(),
                                    author = "Carlos Morales",
                                    title = "Anuncio importante",
                                    message = "Mañana no hay clases",
                                    expiration = 2
                                ),
                                NotificationDomain(
                                    messageId = 1,
                                    messageDate = emptyList(),
                                    author = "Carlos Morales",
                                    title = "Anuncio importante",
                                    message = "Mañana no hay clases",
                                    expiration = 2
                                )
                            )
                        ),
                        CourseDomain(
                            course = "Sala Verde",
                            courseId = 1,
                            notifications = listOf(
                                NotificationDomain(
                                    messageId = 1,
                                    messageDate = emptyList(),
                                    author = "Carlos Morales",
                                    title = "Anuncio importante",
                                    message = "Mañana no hay clases",
                                    expiration = 2
                                )
                            )
                        )
                    )
                )
            )
        )
    }
}