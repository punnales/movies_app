package com.punnales.moviesapp.data.local.room.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.punnales.moviesapp.core.interactors.movie.AddResourceRouteList
import com.punnales.moviesapp.data.local.room.dto.MovieDTO
import com.punnales.moviesapp.data.local.room.dto.ResourceRouteDTO
import kotlinx.coroutines.flow.Flow

@Dao
abstract class MovieDao {

    @Query("SELECT * FROM movie WHERE id = :movieId")
    abstract fun getMovie(movieId: Long): MovieDTO

    @Query("SELECT * FROM movie")
    abstract fun getPagedMovies(): PagingSource<Int, MovieDTO>

    @Insert(onConflict = REPLACE)
    abstract fun insertMovieList(movieList: List<MovieDTO>)

    @Insert(onConflict = IGNORE)
    abstract fun insertResourceRouteList(resourceRouteList: List<ResourceRouteDTO>)

    @Query("SELECT * FROM resource_route")
    abstract fun getResourceRouteList(): Flow<List<ResourceRouteDTO>>
}