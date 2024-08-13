package com.nocountry.edunotify.data.api.model.schools

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CourseInfo(
    val course: String,
    val courseId: Int
) : Parcelable