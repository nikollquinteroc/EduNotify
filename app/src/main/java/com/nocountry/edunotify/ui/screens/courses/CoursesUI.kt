package com.nocountry.edunotify.ui.screens.courses

import com.nocountry.edunotify.domain.model.SchoolInfoDomain
import com.nocountry.edunotify.domain.model.UserDomain

data class CoursesUI(
    val schoolInfoDomain: SchoolInfoDomain? = null,
    val userDomain: UserDomain? = null
)
