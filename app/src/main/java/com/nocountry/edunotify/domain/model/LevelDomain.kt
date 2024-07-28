package com.nocountry.edunotify.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LevelDomain(
    val schoolLevel: String,
    val years: List<YearDomain>
) : Parcelable
