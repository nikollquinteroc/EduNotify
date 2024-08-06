package com.nocountry.edunotify.ui.screens.notifications

import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.nocountry.edunotify.R
import com.nocountry.edunotify.domain.model.CourseDomain
import com.nocountry.edunotify.domain.model.NotificationDomain
import com.nocountry.edunotify.domain.model.UserDomain
import com.nocountry.edunotify.ui.components.BottomNavigationBar
import com.nocountry.edunotify.ui.components.CircleButtonComponent
import com.nocountry.edunotify.ui.components.SpacerComponent
import com.nocountry.edunotify.ui.components.TopAppBarComponent
import com.nocountry.edunotify.ui.theme.EduNotifyTheme

val fakeUserDomain = UserDomain(
    id = 0,
    name = "Fake name",
    lastName = "Fake last name",
    email = "kake@gmail.com",
    phone = "Fake phone",
    role = "COLABORADOR",
    schoolId = 0,
    courses = null
)

@Composable
fun NotificationsScreen(
    onSubscribeToANewCourse: (Int) -> Unit,
    onGoToNotificationDetail: (NotificationDomain) -> Unit,
    userId: Int,
    viewModel: NotificationsViewModel = viewModel(
        factory = NotificationsViewModel.provideFactory(
            LocalContext.current
        )
    ),
    navController: NavHostController,
) {
    val notificationUiState by viewModel.uiState.collectAsState()
    val userDomain = notificationUiState.notificationUI.userDomain ?: fakeUserDomain

    val updateNotificationDomainDataFromDb by rememberUpdatedState {
        viewModel.getUserById(userId)
    }

    // Usa LaunchedEffect para añadir un listener del ciclo de vida
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lifecycleOwner) {
        Log.d("NotificationsScreen", "LaunchedEffect: Fetching user data for userId: $userId")
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            updateNotificationDomainDataFromDb()
        }
    }

    Scaffold(
        topBar = {
            TopAppBarComponent(
                title = R.string.app_name,
                navigationIcon = { },
                actions = { },
            )
        },
        bottomBar = { BottomNavigationBar(navController, userDomain) },
        floatingActionButton = {
            AddNewCourse(
                schoolId = userDomain.schoolId,
                onSubscribeToANewCourse = onSubscribeToANewCourse
            )
        }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
        ) {
            if (userDomain.courses.isNullOrEmpty().not()) {
                CoursesSectionList(
                    courses = userDomain.courses,
                    onGoToNotificationDetail = onGoToNotificationDetail
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
    onGoToNotificationDetail: (NotificationDomain) -> Unit
) {
    LazyColumn {
        items(courses!!) { courseDomain ->
            CourseSection(
                courseDomain = courseDomain,
                onGoToNotificationDetail = onGoToNotificationDetail
            )
        }
    }
}

@Composable
fun CourseSection(
    courseDomain: CourseDomain,
    onGoToNotificationDetail: (NotificationDomain) -> Unit
) {
    Column {
        Text(
            text = courseDomain.course,
            modifier = Modifier.padding(start = 15.dp, top = 10.dp)
        )
        CoursesCardList(notifications = courseDomain.notifications, onGoToNotificationDetail)
    }
}

@Composable
fun CoursesCardList(
    notifications: List<NotificationDomain>,
    onGoToNotificationDetail: (NotificationDomain) -> Unit
) {
    LazyRow {
        items(notifications) { notification ->
            CourseCard(
                notification = notification,
                modifier = Modifier.padding(10.dp),
                onGoToNotificationDetail = onGoToNotificationDetail
            )
        }
    }
}

@Composable
fun CourseCard(
    notification: NotificationDomain,
    onGoToNotificationDetail: (NotificationDomain) -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = modifier
            .shadow(4.dp, shape = RoundedCornerShape(8.dp))
            .clickable { onGoToNotificationDetail(notification) }
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
                    text = "Expira en ${notification.expiration} días",
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
fun AddNewCourse(
    onSubscribeToANewCourse: (Int) -> Unit,
    modifier: Modifier = Modifier,
    schoolId: Int
) {
    Row(modifier = modifier.padding(bottom = 25.dp)) {
        SpacerComponent(modifier = Modifier.weight(1f))
        CircleButtonComponent(
            onClick = { onSubscribeToANewCourse(schoolId) },
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
            onSubscribeToANewCourse = {},
            onGoToNotificationDetail = {},
            navController = rememberNavController(),
            userId = 0
            /*authDomain = AuthDomain(
                jwt = "fsdg", user = UserDomain(
                    id = 1,
                    name = "Nikoll",
                    lastName = "Quintero",
                    email = "nikoll@gmail.com",
                    phone = "32456433",
                    role = "USUARIO",
                    schoolId = 1,
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
            )*/
        )
    }
}