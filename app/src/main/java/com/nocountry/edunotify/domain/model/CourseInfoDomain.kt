package com.nocountry.edunotify.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CourseInfoDomain(
    val course: String,
    val courseId: Int
): Parcelable
