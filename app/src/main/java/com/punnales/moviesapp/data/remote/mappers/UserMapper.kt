package com.punnales.moviesapp.data.remote.mappers

import com.punnales.moviesapp.core.domain.Transaction
import com.punnales.moviesapp.core.domain.User
import com.punnales.moviesapp.data.remote.dto.response.LoginResponseDTO
import com.punnales.moviesapp.data.remote.dto.response.TransactionListResponseDTO
import com.punnales.moviesapp.data.remote.dto.response.UserResponseDTO

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

fun TransactionListResponseDTO.TransactionDTO.fromRemote() =
    Transaction(
        cinema = cinema ?: "",
        message = message ?: "",
        date = date ?: "",
        points = points ?: ""
    )