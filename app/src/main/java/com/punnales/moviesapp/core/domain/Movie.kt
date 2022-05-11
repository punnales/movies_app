package com.punnales.moviesapp.core.domain

data class Movie(
    val id: Long,
    val name: String,
    val rating: String,
    val genre: String,
    val synopsis: String,
    val length: String,
    val mediaList: List<Media>,
)