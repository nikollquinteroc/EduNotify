package com.nocountry.edunotify.data.api.model.schools

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Year(
    val courses: List<CourseInfo>,
    val year: String
) : Parcelable