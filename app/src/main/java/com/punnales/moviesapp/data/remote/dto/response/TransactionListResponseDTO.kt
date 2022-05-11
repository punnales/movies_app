package com.punnales.moviesapp.data.remote.dto.response

import com.squareup.moshi.Json

data class TransactionListResponseDTO(

    @Json(name = "transactions")
    val transactionList: List<TransactionDTO>,

    ) {
    data class TransactionDTO(

        @Json(name = "cinema")
        val cinema: String?,

        @Json(name = "message")
        val message: String?,

        @Json(name = "date")
        val date: String?,

        @Json(name = "points")
        val points: String?,
    )
}