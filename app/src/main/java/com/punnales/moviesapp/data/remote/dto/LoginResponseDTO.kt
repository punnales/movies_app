package com.punnales.moviesapp.data.remote.dto

import com.squareup.moshi.Json

data class LoginResponseDTO(

    @Json(name = "access_token")
    val accessToken: String,

    @Json(name = "token_type")
    val tokenType: String,

    @Json(name = "username")
    val userName: String,
)