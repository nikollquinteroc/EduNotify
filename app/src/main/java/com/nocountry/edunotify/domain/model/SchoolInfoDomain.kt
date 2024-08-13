package com.nocountry.edunotify.domain.model

import android.os.Parcelable
import com.nocountry.edunotify.data.api.model.schools.Level
import kotlinx.parcelize.Parcelize

@Parcelize
data class SchoolInfoDomain(
    val levels: List<LevelDomain>,
    val name: String
) : Parcelable
