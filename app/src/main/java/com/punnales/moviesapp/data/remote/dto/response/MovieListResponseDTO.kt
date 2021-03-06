package com.punnales.moviesapp.data.remote.dto.response

import com.squareup.moshi.Json

data class MovieListResponseDTO(

    @Json(name = "movies")
    val movieList: List<MovieResponseDTO>,

    @Json(name = "routes")
    val resourceRouteList: List<ResourceRouteDTO>,
)