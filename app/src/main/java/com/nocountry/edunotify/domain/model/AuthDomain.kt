package com.nocountry.edunotify.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuthDomain(
    val jwt: String,
    val user: UserDomain?
): Parcelable
