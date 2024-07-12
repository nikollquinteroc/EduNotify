package com.nocountry.edunotify.domain.mappers

import com.nocountry.edunotify.data.api.model.auth.AuthResponse
import com.nocountry.edunotify.domain.model.AuthDomain

class AuthMapper(private val userMapper: UserMapper) {

    fun mapAuthResponseToAuthDomain(authResponse: AuthResponse): AuthDomain {
        return AuthDomain(
            jwt = authResponse.jwt,
            user = userMapper.mapUserResponseToUserDomain(authResponse.user)
        )
    }
}