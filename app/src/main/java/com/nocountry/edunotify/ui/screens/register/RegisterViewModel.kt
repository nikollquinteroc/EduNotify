package com.nocountry.edunotify.ui.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.nocountry.edunotify.data.api.RetrofitServiceFactory
import com.nocountry.edunotify.data.database.mappers.AuthMapperDb
import com.nocountry.edunotify.data.repository.auth.AuthRepositoryImpl
import com.nocountry.edunotify.data.repository.school.SchoolRepositoryImpl
import com.nocountry.edunotify.domain.mappers.AuthMapper
import com.nocountry.edunotify.domain.mappers.CourseInfoMapper
import com.nocountry.edunotify.domain.mappers.CourseMapper
import com.nocountry.edunotify.domain.mappers.LevelMapper
import com.nocountry.edunotify.domain.mappers.NotificationMapper
import com.nocountry.edunotify.domain.mappers.SchoolInfoMapper
import com.nocountry.edunotify.domain.mappers.SchoolMapper
import com.nocountry.edunotify.domain.mappers.UserMapper
import com.nocountry.edunotify.domain.mappers.YearMapper
import com.nocountry.edunotify.domain.repositories.AuthRepository
import com.nocountry.edunotify.domain.repositories.SchoolRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class RegisterUiState(
    val isLoading: Boolean = false,
    val registerUI: RegisterUI = RegisterUI(),
    val error: String = ""
)

class RegisterViewModel(
    private val authRepository: AuthRepository,
    private val schoolRepository: SchoolRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<RegisterUiState> = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    init {
        getAllSchools()
    }

    private fun getAllSchools() {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            try {
                schoolRepository.getAllSchools().collect { schoolList ->
                    _uiState.update {
                        val registerUI = it.registerUI.copy(schoolList = schoolList)
                        it.copy(
                            isLoading = false,
                            registerUI = registerUI
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Error getting the school list, try again. \n${e.message}"
                    )
                }
            }
        }
    }

    fun register(
        name: String,
        lastName: String,
        email: String,
        password: String,
        phone: String,
        school: Int
    ) {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            try {
                authRepository.register(name, lastName, email, password, phone, school)
                    .collect { authDomain ->
                        _uiState.update {
                            val registerUI = it.registerUI.copy(authDomain = authDomain)
                            it.copy(
                                isLoading = false,
                                registerUI = registerUI
                            )
                        }
                    }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Error registering the user information, try again. \n${e.message}"
                    )
                }
            }
        }
    }

    companion object {
        fun provideFactory(): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val service = RetrofitServiceFactory.makeRetrofitService()

                return RegisterViewModel(
                    AuthRepositoryImpl(
                        service = service,
                        authMapper = AuthMapper(UserMapper(CourseMapper(NotificationMapper()))),
                        authMapperDb = AuthMapperDb()
                    ),
                    SchoolRepositoryImpl(
                        service = service,
                        schoolMapper = SchoolMapper(),
                        schoolInfoMapper = SchoolInfoMapper(LevelMapper(YearMapper(CourseInfoMapper())))
                    )
                ) as T
            }
        }
    }
}