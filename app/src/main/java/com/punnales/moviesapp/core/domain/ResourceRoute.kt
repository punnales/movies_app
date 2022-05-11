package com.punnales.moviesapp.core.domain

data class ResourceRoute(
    val code: ResourceCode,
    val sizes: ResourceRouteSizes,
)

data class ResourceRouteSizes(
    val small: String?,
    val medium: String?,
    val large: String?,
    val xLarge: String?,
)

enum class ResourceSize(val value: String) {
    SMALL("small"),
    MEDIUM("medium"),
    LARGE("large"),
    XLARGE("x-large")
}

enum class ResourceCode(val value: String) {
    POSTER("poster"),
    BACKGROUND_SYNOPSIS("background_synopsis"),
    TRAILER_MP4("trailer_mp4"),
    POSTER_HORIZONTAL("poster_horizontal"),
    RIBBON("ribbon"),
}
