package com.nocountry.edunotify.ui.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.nocountry.edunotify.data.RetrofitServiceFactory
import com.nocountry.edunotify.data.model.register.RegisterResponse
import com.nocountry.edunotify.data.model.user.User
import com.nocountry.edunotify.data.repository.auth.AuthRepositoryImpl
import com.nocountry.edunotify.domain.auth.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class RegisterUiState(
    val isLoading: Boolean = false,
    val registerResponse: RegisterResponse = RegisterResponse(
        jwt = "",
        user = User(
            id = 0,
            name = "",
            lastName = "",
            email = "",
            phone = "",
            role = "",
            school = 0,
            courses = emptyList()
        )
    ),
    val error: String = ""
)

class RegisterViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<RegisterUiState> = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun register(
        name: String,
        lastName: String,
        email: String,
        password: String,
        phone: String,
        school: Int
    ) {
        _uiState.update {
            it.copy(isLoading = true)
        }

        viewModelScope.launch {
            try {
                repository.register(name, lastName, email, password, phone, school)
                    .collect { registerResponse ->
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                registerResponse = registerResponse
                            )
                        }
                    }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
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
                return RegisterViewModel(AuthRepositoryImpl(RetrofitServiceFactory.makeRetrofitService())) as T
            }
        }
    }
}