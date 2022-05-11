package com.punnales.moviesapp.data.remote.dto.response

import com.squareup.moshi.Json

class ResourceRouteDTO(

    @Json(name = "code")
    val code: String,

    @Json(name = "sizes")
    val resourceRouteSizes: ResourceRouteSizesDTO,
) {
    data class ResourceRouteSizesDTO(

        @Json(name = "small")
        val small: String?,

        @Json(name = "medium")
        val medium: String?,

        @Json(name = "large")
        val large: String?,

        @Json(name = "x-large")
        val xLarge: String?,
    )
}