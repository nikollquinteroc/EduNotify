package com.nocountry.edunotify.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDomain(
    val id: Int,
    val name: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val role: String,
    val school: Int,
    val courses: List<CourseDomain>?
): Parcelable
