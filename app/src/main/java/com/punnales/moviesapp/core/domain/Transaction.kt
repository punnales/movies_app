package com.punnales.moviesapp.core.domain

import com.squareup.moshi.Json

data class Transaction(
    val cinema: String,
    val message: String,
    val date: String,
    val points: String,
)