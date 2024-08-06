package com.nocountry.edunotify.data.api.body

data class CreateNewMessageBody(
    val author: String,
    val title: String,
    val message: String,
    val expiration: Int,
)
