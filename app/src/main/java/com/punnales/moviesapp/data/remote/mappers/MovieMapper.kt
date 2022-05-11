package com.punnales.moviesapp.data.remote.mappers

import com.punnales.moviesapp.core.domain.*
import com.punnales.moviesapp.data.remote.dto.MovieListResponseDTO
import com.punnales.moviesapp.data.remote.dto.MovieResponseDTO
import com.punnales.moviesapp.data.remote.dto.ResourceRouteDTO

fun MovieListResponseDTO.fromRemote() =
    Pair(movieList.map(MovieResponseDTO::fromRemote), resourceRouteList.map(ResourceRouteDTO::fromRemote))

fun MovieResponseDTO.fromRemote() =
    Movie(
        id, name, rating, genre, synopsis, length, mediaList.map(MovieResponseDTO.MediaDTO::fromRemote)
    )

fun MovieResponseDTO.MediaDTO.fromRemote() =
    Media(
        resource, type, code
    )

fun ResourceRouteDTO.fromRemote() =
    ResourceRoute(
        ResourceCode.values().find { it.value == code } ?: ResourceCode.POSTER,
        resourceRouteSizes.fromRemote()
    )

fun ResourceRouteDTO.ResourceRouteSizesDTO.fromRemote() =
    ResourceRouteSizes(
        small, medium, large, xLarge
    )
