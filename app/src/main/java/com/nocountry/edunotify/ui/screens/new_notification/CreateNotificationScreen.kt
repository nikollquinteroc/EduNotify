package com.nocountry.edunotify.ui.screens.new_notification

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.nocountry.edunotify.R
import com.nocountry.edunotify.data.utils.OptionsNewMessage
import com.nocountry.edunotify.domain.model.CourseDomain
import com.nocountry.edunotify.domain.model.SchoolDomain
import com.nocountry.edunotify.domain.model.UserDomain
import com.nocountry.edunotify.ui.components.BottomNavigationBar
import com.nocountry.edunotify.ui.components.ButtonComponent
import com.nocountry.edunotify.ui.components.CircleButtonComponent
import com.nocountry.edunotify.ui.components.SpacerComponent
import com.nocountry.edunotify.ui.components.TextButtonComponent
import com.nocountry.edunotify.ui.components.TextFieldComponent
import com.nocountry.edunotify.ui.components.TextFieldEmpty
import com.nocountry.edunotify.ui.components.TopAppBarComponent
import com.nocountry.edunotify.ui.screens.notifications.fakeUserDomain
import com.nocountry.edunotify.ui.theme.EduNotifyTheme

@Composable
fun CreateNotificationScreen(
    navController: NavHostController,
    onBackClicked: () -> Unit,
    userDomain: UserDomain,
    createNotificationViewModel: CreateNotificationViewModel = viewModel(
        factory = CreateNotificationViewModel.provideFactory(LocalContext.current)
    )
) {
    val createNotificationUiState by createNotificationViewModel.uiState.collectAsState()
    val courses = createNotificationUiState.createNotificationUi.courses

    LaunchedEffect(userDomain.schoolId) {
        createNotificationViewModel.getCoursesBySchool(userDomain.schoolId)
    }

    Scaffold(
        topBar = {
            TopAppBarComponent(
                title = R.string.create_notification_top_bar,
                navigationIcon = {
                    CircleButtonComponent(
                        onClick = { onBackClicked() },
                        icon = R.drawable.arrow_back
                    )
                },
                actions = null
            )
        },
        bottomBar = { BottomNavigationBar(navController, userDomain) }
    ) {
        when {
            createNotificationUiState.isLoading -> {
                CircularProgressIndicator()
            }

            createNotificationUiState.error.isNotEmpty() -> {
                Text(text = "Error: ${createNotificationUiState.error}")
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        CreateNotificationForm(
                            navController = navController,
                            userDomain = userDomain,
                            createNotificationViewModel = createNotificationViewModel,
                            courses = courses,
                            errorMessageFromServer = createNotificationUiState.error
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CreateNotificationForm(
    navController: NavHostController,
    userDomain: UserDomain,
    createNotificationViewModel: CreateNotificationViewModel,
    courses: List<CourseDomain>,
    errorMessageFromServer: String,
) {
    var author by rememberSaveable { mutableStateOf("") }
    var title by rememberSaveable { mutableStateOf("") }
    var message by rememberSaveable { mutableStateOf("") }
    var expirationText by rememberSaveable { mutableStateOf("") }
    var expiration by rememberSaveable { mutableIntStateOf(0) }
    val selectSchool by rememberSaveable { mutableStateOf<SchoolDomain?>(null) }
    var selectCourse by rememberSaveable { mutableStateOf<CourseDomain?>(null) }

    var isAuthorEmpty by rememberSaveable { mutableStateOf(false) }
    var isTitleEmpty by rememberSaveable { mutableStateOf(false) }
    var isMessageEmpty by rememberSaveable { mutableStateOf(false) }
    var isExpirationEmpty by rememberSaveable { mutableStateOf(false) }
    var isSchoolSelected by rememberSaveable { mutableStateOf(false) }
    var isCourseSelected by rememberSaveable { mutableStateOf(false) }

    var errorMessage by rememberSaveable { mutableStateOf(errorMessageFromServer) }

    TextFieldComponent(
        title = R.string.author_create_notification_form,
        value = author,
        onValueChange = {
            author = it
            isAuthorEmpty = author.isEmpty()
        },
        label = R.string.name_label,
        leadingIcon = null,
        trailingIcon = { },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        visualTransformation = VisualTransformation.None,
        supportingText = {
            if (isAuthorEmpty) {
                TextFieldEmpty()
            }
        },
        isError = isAuthorEmpty,
    )
    SpacerComponent(height = 5.dp)
    TextFieldComponent(
        title = R.string.title_create_notification_form,
        value = title,
        onValueChange = {
            title = it
            isTitleEmpty = title.isEmpty()
        },
        label = R.string.title_label,
        leadingIcon = null,
        trailingIcon = { },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        visualTransformation = VisualTransformation.None,
        supportingText = {
            if (isTitleEmpty) {
                TextFieldEmpty()
            }
        },
        isError = isTitleEmpty,
    )
    SpacerComponent(height = 5.dp)
    TextFieldComponent(
        title = R.string.message_create_notification_form,
        value = message,
        onValueChange = {
            message = it
            isMessageEmpty = message.isEmpty()
        },
        label = R.string.message_label,
        leadingIcon = null,
        trailingIcon = { },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        visualTransformation = VisualTransformation.None,
        supportingText = {
            if (isMessageEmpty) {
                TextFieldEmpty()
            }
        },
        isError = isMessageEmpty,
    )
    SpacerComponent(height = 5.dp)
    TextFieldComponent(
        title = R.string.expiration_create_notification_form,
        value = expirationText,
        onValueChange = {
            expirationText = it
            expiration = it.toIntOrNull() ?: 0
            isExpirationEmpty = expiration == 0
        },
        label = R.string.expiration_label,
        leadingIcon = null,
        trailingIcon = { },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        visualTransformation = VisualTransformation.None,
        supportingText = {
            if (isExpirationEmpty) {
                TextFieldEmpty()
            }
        },
        isError = isExpirationEmpty,
    )
    SpacerComponent(height = 5.dp)
    Column {
        Text(
            text = stringResource(id = R.string.select_school_or_course_create_notification_form),
            style = MaterialTheme.typography.bodySmall
        )
        SpacerComponent(height = 10.dp)
        TextButtonComponent(
            textLabel = stringResource(id = R.string.school_or_course_tag),
            options = optionsForWhoGoesToMessage,
            getDescription = { it },
            selectedOption = if (isSchoolSelected) OptionsNewMessage.SCHOOL else if (isCourseSelected) OptionsNewMessage.COURSE else null,
            onOptionSelected = { selectOption ->
                when (selectOption) {
                    OptionsNewMessage.SCHOOL -> {
                        isSchoolSelected = true
                        isCourseSelected = false
                    }

                    OptionsNewMessage.COURSE -> {
                        isSchoolSelected = false
                        isCourseSelected = true
                    }
                }
            }
        )
    }
    SpacerComponent(height = 5.dp)
    if (isCourseSelected) {
        Column {
            Text(
                text = stringResource(id = R.string.select_course_create_notification_form),
                style = MaterialTheme.typography.bodySmall
            )
            SpacerComponent(height = 10.dp)
            TextButtonComponent(
                textLabel = stringResource(id = R.string.course_tag),
                options = courses,
                getDescription = { it.course },
                selectedOption = selectCourse,
                onOptionSelected = { selectedCourse ->
                    selectCourse = selectedCourse
                }
            )
        }
        SpacerComponent(height = 10.dp)
    }
    SpacerComponent(height = 10.dp)
    ButtonComponent(
        text = R.string.create_notification_button,
        onClick = {
            errorMessage = ""

            val isValid = author.isNotEmpty() && title.isNotEmpty() && message.isNotEmpty() && expiration != 0
            val isSchoolOrCourseSelected = isSchoolSelected || isCourseSelected

            if (isValid && isSchoolOrCourseSelected) {
                isAuthorEmpty = false
                isTitleEmpty = false
                isMessageEmpty = false
                isExpirationEmpty = false

                val schoolId = selectSchool?.id ?: 0
                val courseId = selectCourse?.courseId ?: 0

                when {
                    isSchoolSelected && isCourseSelected -> {
                        createNotificationViewModel.createNotificationMessageForCourse(
                            author = author,
                            title = title,
                            message = message,
                            expiration = expiration,
                            courseId = courseId
                        )
                    }
                    isSchoolSelected -> {
                        createNotificationViewModel.createNotificationMessageForSchool(
                            author = author,
                            title = title,
                            message = message,
                            expiration = expiration,
                            schoolId = schoolId
                        )
                    }
                }
            } else {
                isAuthorEmpty = author.isEmpty()
                isTitleEmpty = title.isEmpty()
                isMessageEmpty = message.isEmpty()
                isExpirationEmpty = expiration == 0
                if (!isSchoolOrCourseSelected) {
                    errorMessage = "Please select a school or a course."
                }
            }

           /* if (author.isNotEmpty() && title.isNotEmpty() && message.isNotEmpty() && expiration != 0) {
                isAuthorEmpty = false
                isTitleEmpty = false
                isMessageEmpty = false
                isExpirationEmpty = false

                val schoolId = selectSchool?.id ?: 0
                val courseId = selectCourse?.courseId ?: 0

                if (isSchoolSelected && isCourseSelected) {
                    createNotificationViewModel.createNotificationMessageForCourse(
                        author = author,
                        title = title,
                        message = message,
                        expiration = expiration,
                        courseId = courseId
                    )
                } else if (isSchoolSelected) {
                    createNotificationViewModel.createNotificationMessageForSchool(
                        author = author,
                        title = title,
                        message = message,
                        expiration = expiration,
                        schoolId = schoolId
                    )
                }
            } else {
                isAuthorEmpty = author.isEmpty()
                isTitleEmpty = title.isEmpty()
                isMessageEmpty = message.isEmpty()
                isExpirationEmpty = expiration == 0
            }*/
        },
        isSelected = false
    )
    SpacerComponent(height = 16.dp)
    Text(
        text = errorMessage.ifEmpty { "" },
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.error,
        modifier = Modifier.padding(16.dp)
    )
}

val optionsForWhoGoesToMessage = listOf(OptionsNewMessage.SCHOOL, OptionsNewMessage.COURSE)


@Preview(showBackground = true)
@Composable
fun CreateNotificationScreenPreview() {
    EduNotifyTheme {
        CreateNotificationScreen(
            navController = rememberNavController(),
            onBackClicked = { /*TODO*/ },
            userDomain = fakeUserDomain
        )
    }
}