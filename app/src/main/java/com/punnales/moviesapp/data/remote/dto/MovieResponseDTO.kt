package com.punnales.moviesapp.data.remote.dto

import com.punnales.moviesapp.core.domain.Media
import com.squareup.moshi.Json

data class MovieResponseDTO(

    @Json(name = "id")
    val id: Long,

    @Json(name = "name")
    val name: String,

    @Json(name = "rating")
    val rating: String,

    @Json(name = "genre")
    val genre: String,

    @Json(name = "synopsis")
    val synopsis: String,

    @Json(name = "length")
    val length: String,

    @Json(name = "media")
    val mediaList: List<MediaDTO>,
) {

    data class MediaDTO(

        @Json(name = "resource")
        val resource: String,

        @Json(name = "type")
        val type: String,

        @Json(name = "code")
        val code: String,
    )

}