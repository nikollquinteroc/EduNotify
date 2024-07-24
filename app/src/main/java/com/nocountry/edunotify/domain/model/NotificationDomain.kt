package com.nocountry.edunotify.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class NotificationDomain(
    val messageId: Int,
    val messageDate: List<Int>,
    val author: String,
    val title: String,
    val message: String,
    val expiration: Int
) : Parcelable
