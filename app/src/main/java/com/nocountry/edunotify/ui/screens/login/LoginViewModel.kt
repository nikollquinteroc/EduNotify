package com.nocountry.edunotify.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.nocountry.edunotify.data.api.RetrofitServiceFactory
import com.nocountry.edunotify.data.repository.auth.AuthRepositoryImpl
import com.nocountry.edunotify.domain.mappers.AuthMapper
import com.nocountry.edunotify.domain.mappers.CourseMapper
import com.nocountry.edunotify.domain.mappers.NotificationMapper
import com.nocountry.edunotify.domain.mappers.UserMapper
import com.nocountry.edunotify.domain.model.AuthDomain
import com.nocountry.edunotify.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoginUiState(
    val isLoginUiState: Boolean = false,
    val loginResponse: AuthDomain = AuthDomain(jwt = "", user = null),
    val error: String = ""
)

class LoginViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun login(email: String, password: String) {
        _uiState.update { it.copy(isLoginUiState = true) }

        viewModelScope.launch {
            try {
                repository.login(email, password).collect { loginResponse ->
                    _uiState.update {
                        it.copy(
                            isLoginUiState = false,
                            loginResponse = loginResponse
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoginUiState = false,
                        error = "Network request failed, try again. \n${e.message}"
                    )
                }
            }
        }
    }

    companion object {
        fun provideFactory(): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return LoginViewModel(
                    AuthRepositoryImpl(
                        service = RetrofitServiceFactory.makeRetrofitService(),
                        authMapper = AuthMapper(UserMapper(CourseMapper(NotificationMapper())))
                    )
                ) as T
            }
        }
    }
}