package com.punnales.moviesapp.data.local.room.mappers

import com.punnales.moviesapp.core.domain.Movie
import com.punnales.moviesapp.data.local.room.dto.MovieDTO

fun Movie.toDatabase() =
    MovieDTO(
        id, name, rating, genre, synopsis, length, mediaList
    )

fun MovieDTO.fromDatabase() =
    Movie(
        id, name, rating, genre, synopsis, length, mediaList
    )