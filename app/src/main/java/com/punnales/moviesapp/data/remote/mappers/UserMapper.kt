package com.punnales.moviesapp.data.remote.mappers

import com.punnales.moviesapp.core.domain.User
import com.punnales.moviesapp.data.remote.dto.LoginResponseDTO

fun LoginResponseDTO.fromRemote() =
    User(
        accessToken = accessToken,
        tokenType = tokenType,
        userName = userName
    )