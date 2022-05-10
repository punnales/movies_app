package com.punnales.moviesapp.data.remote.mappers

import com.punnales.moviesapp.core.domain.User
import com.punnales.moviesapp.data.remote.dto.LoginResponseDTO
import com.punnales.moviesapp.data.remote.dto.UserResponseDTO

fun LoginResponseDTO.fromRemote() =
    User(
        accessToken = accessToken,
        tokenType = tokenType,
        userName = userName
    )

fun UserResponseDTO.fromRemote() =
    User(
        email = email ?: "",
        firstName = firstName ?: "",
        lastName = lastName ?: "",
        phoneNumber = phoneNumber ?: "",
        profilePicture = profilePicture,
        cardNumber = cardNumber ?: "",
    )