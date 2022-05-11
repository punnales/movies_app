package com.punnales.moviesapp.data.remote.dto.response

import com.squareup.moshi.Json

data class UserResponseDTO(

    @Json(name = "email")
    val email: String?,

    @Json(name = "first_name")
    val firstName: String?,

    @Json(name = "last_name")
    val lastName: String?,

    @Json(name = "phone_number")
    val phoneNumber: String?,

    @Json(name = "profile_picture")
    val profilePicture: String?,

    @Json(name = "card_number")
    val cardNumber: String?,
)
