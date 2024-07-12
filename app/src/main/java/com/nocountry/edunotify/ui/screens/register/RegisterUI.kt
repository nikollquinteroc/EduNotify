package com.nocountry.edunotify.ui.screens.register

import com.nocountry.edunotify.domain.model.AuthDomain
import com.nocountry.edunotify.domain.model.SchoolDomain

data class RegisterUI(
    val authDomain: AuthDomain? = null,
    val schoolList: List<SchoolDomain>? = null
)
