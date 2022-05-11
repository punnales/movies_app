package com.punnales.moviesapp.data.local.room.datasource

import androidx.paging.PagingSource
import com.punnales.moviesapp.core.domain.Movie
import com.punnales.moviesapp.core.domain.ResourceRoute
import com.punnales.moviesapp.data.local.room.dto.MovieDTO
import com.punnales.moviesapp.data.remote.utils.BaseDataSource
import com.punnales.moviesapp.data.remote.utils.Resource
import kotlinx.coroutines.flow.Flow

//Specify use of resource route
interface MovieLocalDataSource : BaseDataSource {

    suspend fun addMovieList(movieList: List<Movie>): Resource<Unit>

    fun getMovieList(): PagingSource<Int, MovieDTO>

    suspend fun addResourceRouteList(resourceRouteList: List<ResourceRoute>): Resource<Unit>

    suspend fun getResourceRouteList(): Flow<List<ResourceRoute>>

    suspend fun getMovie(movieId: Long): Resource<Movie>

}