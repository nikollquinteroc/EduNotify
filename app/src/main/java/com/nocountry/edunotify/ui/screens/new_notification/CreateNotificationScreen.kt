package com.nocountry.edunotify.ui.screens.new_notification

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.nocountry.edunotify.R
import com.nocountry.edunotify.data.utils.OptionsNewMessage
import com.nocountry.edunotify.domain.model.UserDomain
import com.nocountry.edunotify.ui.components.BottomNavigationBar
import com.nocountry.edunotify.ui.components.ButtonComponent
import com.nocountry.edunotify.ui.components.CircleButtonComponent
import com.nocountry.edunotify.ui.components.SpacerComponent
import com.nocountry.edunotify.ui.components.TextButtonComponent
import com.nocountry.edunotify.ui.components.TextFieldComponent
import com.nocountry.edunotify.ui.components.TextFieldEmpty
import com.nocountry.edunotify.ui.components.TopAppBarComponent
import com.nocountry.edunotify.ui.navigation.Destinations
import com.nocountry.edunotify.ui.screens.notifications.fakeUserDomain
import com.nocountry.edunotify.ui.theme.EduNotifyTheme

@Composable
fun CreateNotificationScreen(
    navController: NavHostController,
    onBackClicked: () -> Unit,
    userDomain: UserDomain
) {
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
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                CreateNotificationForm(navController, userDomain)
            }
        }
    }
}

@Composable
fun CreateNotificationForm(navController: NavHostController, userDomain: UserDomain) {
    var author by rememberSaveable { mutableStateOf("") }
    var title by rememberSaveable { mutableStateOf("") }
    var message by rememberSaveable { mutableStateOf("") }
    var expiration by rememberSaveable { mutableIntStateOf(0) }

    var isAuthorEmpty by rememberSaveable { mutableStateOf(false) }
    var isTitleEmpty by rememberSaveable { mutableStateOf(false) }
    var isMessageEmpty by rememberSaveable { mutableStateOf(false) }
    var isExpirationEmpty by rememberSaveable { mutableStateOf(false) }

    var isCreatedNotification by rememberSaveable { mutableStateOf(false) }

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
        readOnly = !isAuthorEmpty
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
        readOnly = !isTitleEmpty
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
        readOnly = !isMessageEmpty
    )
    SpacerComponent(height = 5.dp)
    TextFieldComponent(
        title = R.string.expiration_create_notification_form,
        value = expiration.toString(),
        onValueChange = {
            expiration = it.toInt()
            isExpirationEmpty = expiration == 0
        },
        label = R.string.expiration_label,
        leadingIcon = null,
        trailingIcon = { },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        visualTransformation = VisualTransformation.None,
        supportingText = {
            if (isExpirationEmpty) {
                TextFieldEmpty()
            }
        },
        isError = isExpirationEmpty,
        readOnly = !isExpirationEmpty
    )
    SpacerComponent(height = 5.dp)
    /*Column {
        Text(
            text = stringResource(id = R.string.select_school_or_course_create_notification_form),
            style = MaterialTheme.typography.bodySmall
        )
        SpacerComponent(height = 10.dp)
        TextButtonComponent(
            textLabel = stringResource(id = R.string.school_tag),
            options = optionsForWhoGoesToMessage,
            getDescription = {  },
            selectedOption = selectedSchool,
            onOptionSelected = { selectedSchool = it }
        )
    }*/
    SpacerComponent(height = 5.dp)
    ButtonComponent(
        text = R.string.create_notification_button,
        onClick = {
            navController.navigate(Destinations.TAB_LOGIN_REGISTER) {
                popUpTo(Destinations.TAB_LOGIN_REGISTER) { inclusive = true }
                launchSingleTop = true
            }
        },
        isSelected = isCreatedNotification
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