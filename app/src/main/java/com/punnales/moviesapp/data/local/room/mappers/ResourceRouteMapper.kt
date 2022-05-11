package com.punnales.moviesapp.data.local.room.mappers

import com.punnales.moviesapp.core.domain.ResourceCode
import com.punnales.moviesapp.core.domain.ResourceRoute
import com.punnales.moviesapp.data.local.room.dto.ResourceRouteDTO

fun ResourceRoute.toDatabase() =
    ResourceRouteDTO(
        code.value,
        sizes
    )

fun ResourceRouteDTO.fromDatabase() =
    ResourceRoute(
        ResourceCode.values().find { it.value == code } ?: ResourceCode.POSTER,
        resourceRouteSizes
    )