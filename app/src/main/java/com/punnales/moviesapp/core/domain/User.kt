package com.punnales.moviesapp.core.domain

data class User(
    val accessToken: String? = null,
    val tokenType: String? = null,
    val userName: String? = null,
    val email: String? = null,
    val firstName: String = "",
    val lastName: String = "",
    val phoneNumber: String = "",
    val profilePicture: String? = null,
    val cardNumber: String? = null,
)