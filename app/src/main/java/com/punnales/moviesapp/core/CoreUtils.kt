package com.punnales.moviesapp.core

import com.punnales.moviesapp.core.domain.*

fun Movie.getMediaUrl(resourceRouteList: List<ResourceRoute>, resourceCode: ResourceCode, size: ResourceSize) =
    resourceRouteList.find { it.code == resourceCode }?.getSizeUrl(size) + mediaList.find { it.code == resourceCode.value }?.resource

fun ResourceRoute.getSizeUrl(size: ResourceSize) =
    when (size) {
        ResourceSize.SMALL -> sizes.small
        ResourceSize.MEDIUM -> sizes.medium
        ResourceSize.LARGE -> sizes.large
        ResourceSize.XLARGE -> sizes.xLarge
    }