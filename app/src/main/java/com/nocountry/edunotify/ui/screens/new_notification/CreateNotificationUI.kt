package com.nocountry.edunotify.ui.screens.new_notification

import com.nocountry.edunotify.domain.model.CourseDomain
import com.nocountry.edunotify.domain.model.SchoolDomain
import com.nocountry.edunotify.domain.model.UserDomain

data class CreateNotificationUI(
    val userDomain: UserDomain? = null,
    val courses: List<CourseDomain> = emptyList(),
    val schoolList: List<SchoolDomain> = emptyList(),
    val messageCreated: String = ""
)
