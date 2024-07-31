package com.nocountry.edunotify.ui.screens.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nocountry.edunotify.R
import com.nocountry.edunotify.domain.model.AuthDomain
import com.nocountry.edunotify.domain.model.SchoolDomain
import com.nocountry.edunotify.ui.components.ButtonComponent
import com.nocountry.edunotify.ui.components.SpacerComponent
import com.nocountry.edunotify.ui.components.TextButtonComponent
import com.nocountry.edunotify.ui.components.TextFieldComponent
import com.nocountry.edunotify.ui.components.TextFieldEmpty
import com.nocountry.edunotify.ui.theme.EduNotifyTheme

@Composable
fun RegisterScreen(
    navigateToNotifications: (AuthDomain) -> Unit,
    registerViewModel: RegisterViewModel = viewModel(factory = RegisterViewModel.provideFactory(
        LocalContext.current))
) {
    val registerUiState by registerViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val authDomain = registerUiState.registerUI.authDomain
        val schoolList = registerUiState.registerUI.schoolList

        when {
            registerUiState.isLoading -> {
                CircularProgressIndicator()
            }

            authDomain != null -> {
                navigateToNotifications(authDomain)
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(15.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        RegisterFields(
                            registerViewModel = registerViewModel,
                            schools = schoolList ?: emptyList(),
                            errorMessageFromServer = registerUiState.error
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RegisterFields(
    registerViewModel: RegisterViewModel,
    errorMessageFromServer: String,
    schools: List<SchoolDomain>
) {
    var name by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var selectedSchool by rememberSaveable { mutableStateOf<SchoolDomain?>(null) }
    var mail by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    var errorMessage by rememberSaveable { mutableStateOf(errorMessageFromServer) }

    var isNameEmpty by rememberSaveable { mutableStateOf(false) }
    var isLastNameEmpty by rememberSaveable { mutableStateOf(false) }
    var isMailEmpty by rememberSaveable { mutableStateOf(false) }
    var isPhoneEmpty by rememberSaveable { mutableStateOf(false) }
    var isPasswordEmpty by rememberSaveable { mutableStateOf(false) }

    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    Image(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = "logo",
        modifier = Modifier.size(180.dp)
    )
    SpacerComponent(height = 5.dp)
    TextFieldComponent(
        title = R.string.name,
        value = name,
        onValueChange = {
            name = it
            isNameEmpty = name.isEmpty()
        },
        label = R.string.name_label,
        leadingIcon = null,
        trailingIcon = { },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        visualTransformation = VisualTransformation.None,
        supportingText = {
            if (isNameEmpty) {
                TextFieldEmpty()
            }
        },
        isError = isNameEmpty
    )
    SpacerComponent(height = 5.dp)
    TextFieldComponent(
        title = R.string.last_name,
        value = lastName,
        onValueChange = {
            lastName = it
            isLastNameEmpty = lastName.isEmpty()
        },
        label = R.string.last_name_label,
        leadingIcon = null,
        trailingIcon = { },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        visualTransformation = VisualTransformation.None,
        supportingText = {
            if (isLastNameEmpty) {
                TextFieldEmpty()
            }
        },
        isError = isLastNameEmpty
    )
    SpacerComponent(height = 5.dp)
    Column {
        Text(
            text = stringResource(id = R.string.school), style = MaterialTheme.typography.bodySmall
        )
        SpacerComponent(height = 10.dp)
        TextButtonComponent(
            textLabel = stringResource(id = R.string.school_tag),
            options = schools,
            getDescription = { it.name },
            selectedOption = selectedSchool,
            onOptionSelected = { selectedSchool = it }
        )
    }
    SpacerComponent(height = 10.dp)
    TextFieldComponent(
        title = R.string.mail,
        value = mail,
        onValueChange = {
            mail = it
            isMailEmpty = mail.isEmpty()
        },
        label = R.string.mail_label,
        leadingIcon = Icons.Default.Email,
        trailingIcon = { },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        visualTransformation = VisualTransformation.None,
        supportingText = {
            if (isMailEmpty) {
                TextFieldEmpty()
            }
        },
        isError = isMailEmpty
    )
    SpacerComponent(height = 5.dp)
    TextFieldComponent(
        title = R.string.phone,
        value = phone,
        onValueChange = {
            phone = it
            isPhoneEmpty = phone.isEmpty()
        },
        label = R.string.phone_label,
        leadingIcon = null,
        trailingIcon = { },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        visualTransformation = VisualTransformation.None,
        supportingText = {
            if (isPhoneEmpty) {
                TextFieldEmpty()
            }
        },
        isError = isPhoneEmpty
    )
    SpacerComponent(height = 5.dp)
    TextFieldComponent(
        title = R.string.password,
        value = password,
        onValueChange = {
            password = it
            isPasswordEmpty = password.isEmpty()
        },
        label = R.string.password_label,
        leadingIcon = null,
        trailingIcon = {
            if (password.isNotEmpty()) {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    if (passwordVisible) {
                        Icon(imageVector = Icons.Default.VisibilityOff, contentDescription = null)
                    } else {
                        Icon(imageVector = Icons.Default.Visibility, contentDescription = null)
                    }
                }
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        supportingText = {
            if (isPasswordEmpty) {
                TextFieldEmpty()
            }
        },
        isError = isPasswordEmpty
    )
    SpacerComponent(height = 15.dp)
    ButtonComponent(
        text = R.string.register, onClick = {
            errorMessage = ""

            if (name.isNotEmpty() && lastName.isNotEmpty() && mail.isNotEmpty() && phone.isNotEmpty() && password.isNotEmpty()) {
                isNameEmpty = false
                isLastNameEmpty = false
                isMailEmpty = false
                isPhoneEmpty = false
                isPasswordEmpty = false

                val schoolId = selectedSchool?.id ?: 0

                registerViewModel.register(
                    name = name,
                    lastName = lastName,
                    email = mail,
                    password = password,
                    phone = phone,
                    school = schoolId
                )
            } else {
                isNameEmpty = name.isEmpty()
                isLastNameEmpty = lastName.isEmpty()
                isMailEmpty = mail.isEmpty()
                isPhoneEmpty = phone.isEmpty()
                isPasswordEmpty = password.isEmpty()
            }
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

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    EduNotifyTheme {
        //RegisterScreen(schools = schools, navigateToHome = {})
    }
}