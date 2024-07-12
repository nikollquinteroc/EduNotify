package com.nocountry.edunotify.ui.screens.notifications

import android.content.Context
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.nocountry.edunotify.data.api.RetrofitServiceFactory
import com.nocountry.edunotify.data.database.EduNotifyRoomDatabase
import com.nocountry.edunotify.data.database.mappers.AuthMapperDb
import com.nocountry.edunotify.data.repository.auth.AuthRepositoryImpl
import com.nocountry.edunotify.domain.mappers.AuthMapper
import com.nocountry.edunotify.domain.mappers.CourseMapper
import com.nocountry.edunotify.domain.mappers.NotificationMapper
import com.nocountry.edunotify.domain.mappers.UserMapper
import com.nocountry.edunotify.domain.repositories.AuthRepository
import com.nocountry.edunotify.ui.navigation.Destinations
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class NotificationUiState(
    val isLoading: Boolean = false,
    val notificationUI: NotificationUI = NotificationUI(),
    val error: String = ""
)

class NotificationsViewModel(
    private val authRepository: AuthRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _uiState: MutableStateFlow<NotificationUiState> = MutableStateFlow(
        NotificationUiState()
    )
    val uiState: StateFlow<NotificationUiState> = _uiState.asStateFlow()

    init {
        val userId: Int = checkNotNull(savedStateHandle[Destinations.USER_ID])
        getUserById(userId)
    }

    fun getUserById(userId: Int) {
        _uiState.update { it.copy(isLoading = true) }

        Log.d("NotificationsViewModel", "Fetching user data for userId: $userId")
        viewModelScope.launch {
            try {
                authRepository.getUserById(userId).collect { userDomain ->
                    _uiState.update {
                        val notificationUI = it.notificationUI.copy(userDomain = userDomain)
                        it.copy(
                            isLoading = false,
                            notificationUI = notificationUI
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Error trying to get user by id, try again. \n${e.message}"
                    )
                }
            }
        }
    }

    companion object {
        fun provideFactory(context: Context): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras
                ): T {
                    val service = RetrofitServiceFactory.makeRetrofitService()
                    val savedStateHandle = extras.createSavedStateHandle()
                    return NotificationsViewModel(
                        AuthRepositoryImpl(
                            service = service,
                            authMapper = AuthMapper(UserMapper(CourseMapper(NotificationMapper()))),
                            authMapperDb = AuthMapperDb(),
                            userDao = EduNotifyRoomDatabase.getDatabase(context).getUserDao()
                        ),
                        savedStateHandle
                    ) as T
                }
            }
    }
}