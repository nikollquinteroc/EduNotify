package com.nocountry.edunotify.ui.screens.notifications

import androidx.lifecycle.ViewModel
import com.nocountry.edunotify.domain.model.NotificationDomain
import com.nocountry.edunotify.domain.repositories.NotificationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class NotificationUiState(
    val isLoading: Boolean = false,
    val notificationResponse: NotificationDomain = NotificationDomain(
        messageId = 0,
        messageDate = emptyList(),
        author = "",
        title = "",
        message = "",
        expiration = 0
    ),
    val error: String = ""
)

class NotificationsViewModel(val notificationRepository: NotificationRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<NotificationUiState> = MutableStateFlow(
        NotificationUiState()
    )
    val uiState: StateFlow<NotificationUiState> = _uiState.asStateFlow()
}