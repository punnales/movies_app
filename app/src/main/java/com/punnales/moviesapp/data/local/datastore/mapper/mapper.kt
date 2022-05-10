package com.punnales.moviesapp.data.local.datastore.mapper

import com.punnales.moviesapp.UserProto
import com.punnales.moviesapp.core.domain.User

fun UserProto.fomDatastore(): User =
    User(
        accessToken = accessToken,
        tokenType = tokenType,
        userName = userName,
        email = email,
        firstName = firstName,
        lastName = lastName,
        phoneNumber = phoneNumber,
        cardNumber = cardNumber
    )
