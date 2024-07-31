package com.nocountry.edunotify.ui.screens.courses

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
import com.nocountry.edunotify.data.repository.school.SchoolRepositoryImpl
import com.nocountry.edunotify.domain.mappers.CourseInfoMapper
import com.nocountry.edunotify.domain.mappers.CourseMapper
import com.nocountry.edunotify.domain.mappers.LevelMapper
import com.nocountry.edunotify.domain.mappers.NotificationMapper
import com.nocountry.edunotify.domain.mappers.SchoolInfoMapper
import com.nocountry.edunotify.domain.mappers.SchoolMapper
import com.nocountry.edunotify.domain.mappers.UserMapper
import com.nocountry.edunotify.domain.mappers.YearMapper
import com.nocountry.edunotify.domain.repositories.CourseRepository
import com.nocountry.edunotify.domain.repositories.SchoolRepository
import com.nocountry.edunotify.ui.navigation.Destinations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class CoursesUiState(
    val isLoading: Boolean = false,
    val coursesUI: CoursesUI = CoursesUI(),
    val error: String = ""
)

class CoursesViewModel(
    savedStateHandle: SavedStateHandle,
    private val schoolRepository: SchoolRepository,
    private val courseRepository: CourseRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<CoursesUiState> = MutableStateFlow(CoursesUiState())
    val uiState: StateFlow<CoursesUiState> = _uiState.asStateFlow()

    init {
        val schoolId: Int? = savedStateHandle[Destinations.SCHOOL_ID]
        if (schoolId != null) {
            getSchoolInfo(schoolId)
        } else {
            // Manejar el caso en que schoolId es nulo
            // Podrías lanzar una excepción, mostrar un mensaje de error, o tomar una acción alternativa
            Log.e("CoursesViewModel", "School ID is null")
        }
    }
    /*
    init {
        val schoolId: Int = checkNotNull(savedStateHandle[Destinations.SCHOOL_ID])
        getSchoolInfo(schoolId)
    }*/

    private fun getSchoolInfo(schoolId: Int) {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            try {
                schoolRepository.getSchoolInfo(schoolId).collect { schoolInfoDomain ->
                    _uiState.update {
                        val coursesUi = it.coursesUI.copy(schoolInfoDomain = schoolInfoDomain)
                        it.copy(isLoading = false, coursesUI = coursesUi)
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Error getting the school info, try again. \n${e.message}"
                    )
                }

            }
        }
    }

    fun assignCourseToUser(courseId: Int, userId: Int) {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    courseRepository.assignCourseToUser(courseId, userId).collect { userDomain ->
                        _uiState.update {
                            val coursesUi = it.coursesUI.copy(userDomain = userDomain)
                            it.copy(isLoading = false, coursesUI = coursesUi)
                        }
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Error assigning the course to the user, please try again. \n" +
                                "${e.message}"
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
                    val savedStateHandle = extras.createSavedStateHandle()
                    val service = RetrofitServiceFactory.makeRetrofitService()
                    val database = EduNotifyRoomDatabase.getDatabase(context = context)
                    val userDao = database.getUserDao()

                    return CoursesViewModel(
                        savedStateHandle,
                        SchoolRepositoryImpl(
                            service = service,
                            schoolMapper = SchoolMapper(),
                            schoolInfoMapper = SchoolInfoMapper(
                                LevelMapper(YearMapper(CourseInfoMapper()))
                            )
                        ),
                        CourseRepositoryImpl(
                            service = service,
                            courseMapper = CourseMapper(
                                NotificationMapper()
                            ),
                            userMapper = UserMapper(CourseMapper(NotificationMapper())),
                            userDao = userDao
                        )
                    ) as T
                }
            }
    }
}