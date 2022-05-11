package com.punnales.moviesapp.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.punnales.moviesapp.data.local.room.daos.MovieDao
import com.punnales.moviesapp.data.local.room.dto.MovieDTO
import com.punnales.moviesapp.data.local.room.dto.ResourceRouteDTO
import com.punnales.moviesapp.data.local.room.mappers.RoomTypeConverter

@Database(
    entities = [
        MovieDTO::class,
        ResourceRouteDTO::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(RoomTypeConverter::class)
abstract class MovieAppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        const val DATABASE_NAME = "movies.db"
    }
}