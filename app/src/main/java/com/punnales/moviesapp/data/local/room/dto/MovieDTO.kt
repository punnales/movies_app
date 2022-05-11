package com.punnales.moviesapp.data.local.room.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.punnales.moviesapp.core.domain.Media

@Entity(tableName = "movie")
data class MovieDTO(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "rating")
    val rating: String,

    @ColumnInfo(name = "genre")
    val genre: String,

    @ColumnInfo(name = "synopsis")
    val synopsis: String,

    @ColumnInfo(name = "length")
    val length: String,

    @ColumnInfo(name = "mediaList")
    val mediaList: List<Media>,
)