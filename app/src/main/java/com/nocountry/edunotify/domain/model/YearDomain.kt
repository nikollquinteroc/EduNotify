package com.nocountry.edunotify.domain.model

import android.os.Parcelable
import com.nocountry.edunotify.data.api.model.schools.CourseInfo
import kotlinx.parcelize.Parcelize

@Parcelize
data class YearDomain(
    val courses: List<CourseInfoDomain>,
    val year: String
): Parcelable
