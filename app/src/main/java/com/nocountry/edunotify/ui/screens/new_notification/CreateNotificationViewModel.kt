package com.nocountry.edunotify.ui.screens.new_notification

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
import com.nocountry.edunotify.data.repository.course.CourseRepositoryImpl
import com.nocountry.edunotify.data.repository.notifications.NotificationsRepositoryImpl
import com.nocountry.edunotify.domain.mappers.CourseMapper
import com.nocountry.edunotify.domain.mappers.NotificationMapper
import com.nocountry.edunotify.domain.mappers.UserMapper
import com.nocountry.edunotify.domain.repositories.CourseRepository
import com.nocountry.edunotify.domain.repositories.NotificationRepository
import com.nocountry.edunotify.domain.repositories.SchoolRepository
import com.nocountry.edunotify.ui.navigation.Destinations
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CreateNotificationUiState(
    val isLoading: Boolean = false,
    val createNotificationUi: CreateNotificationUI = CreateNotificationUI(),
    val error: String = ""
)

class CreateNotificationViewModel(
    private val notificationRepository: NotificationRepository,
    private val courseRepository: CourseRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _uiState: MutableStateFlow<CreateNotificationUiState> = MutableStateFlow(
        CreateNotificationUiState()
    )
    val uiState: StateFlow<CreateNotificationUiState> = _uiState.asStateFlow()

    init {
        val schoolId: Int? = savedStateHandle[Destinations.SCHOOL_ID]
        if (schoolId != null) {
            getCoursesBySchool(schoolId)
        } else {
            Log.e("CoursesViewModel", "School ID is null")
        }
    }

    fun getCoursesBySchool(schoolId: Int) {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            try {
                courseRepository.getCoursesBySchool(schoolId).collect { courseDomainList ->
                    Log.d("CreateNotificationViewModel", "Courses received: $courseDomainList")
                    _uiState.update {
                        val createNotificationUi =
                            it.createNotificationUi.copy(courses = courseDomainList)
                        it.copy(isLoading = false, createNotificationUi = createNotificationUi)
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Error getting courses by school, try again. \n${e.message}"
                    )
                }

            }
        }
    }

    fun createNotificationMessageForCourse(
        author: String,
        title: String,
        message: String,
        expiration: Int,
        courseId: Int
    ) {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            try {
                notificationRepository.createNotificationMessageForCourse(
                    author,
                    title,
                    message,
                    expiration,
                    courseId
                ).collect { messageCreated ->
                    _uiState.update {
                        val createNotificationUi =
                            it.createNotificationUi.copy(messageCreated = messageCreated)
                        it.copy(isLoading = false, createNotificationUi = createNotificationUi)
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Error creating notification message for course, try again. \n${e.message}"
                    )
                }
            }
        }
    }

    fun createNotificationMessageForSchool(
        author: String,
        title: String,
        message: String,
        expiration: Int,
        schoolId: Int
    ) {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            try {
                notificationRepository.createNotificationMessageForCourse(
                    author,
                    title,
                    message,
                    expiration,
                    schoolId
                ).collect { messageCreated ->
                    _uiState.update {
                        val createNotificationUi =
                            it.createNotificationUi.copy(messageCreated = messageCreated)
                        it.copy(isLoading = false, createNotificationUi = createNotificationUi)
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Error creating notification message for school, try again. \n${e.message}"
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
                    val database = EduNotifyRoomDatabase.getDatabase(context = context)
                    val userDao = database.getUserDao()
                    return CreateNotificationViewModel(
                        NotificationsRepositoryImpl(service),
                        CourseRepositoryImpl(
                            service = service,
                            courseMapper = CourseMapper(
                                NotificationMapper()
                            ),
                            userMapper = UserMapper(CourseMapper(NotificationMapper())),
                            userDao = userDao
                        ),
                        savedStateHandle
                    ) as T
                }
            }
    }
}
