package com.nocountry.edunotify.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CourseDomain(
    val course: String,
    val courseId: Int,
    val notifications: List<NotificationDomain>
): Parcelable
