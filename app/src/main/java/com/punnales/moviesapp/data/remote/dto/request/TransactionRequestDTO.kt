package com.punnales.moviesapp.data.remote.dto.request

import com.squareup.moshi.Json

data class TransactionRequestDTO(

    @Json(name = "card_number")
    val cardNumber: String,

    @Json(name = "country_code")
    val countryCode: String = "MX",

    @Json(name = "pin")
    val pin: String = "1234",

    @Json(name = "transaction_include")
    val transactionInclude: Boolean = true,
)