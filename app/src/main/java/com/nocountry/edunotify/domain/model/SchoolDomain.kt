package com.nocountry.edunotify.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SchoolDomain(
    val id: Int,
    val name: String
): Parcelable
