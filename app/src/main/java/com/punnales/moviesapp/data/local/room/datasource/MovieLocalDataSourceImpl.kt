package com.punnales.moviesapp.data.local.room.datasource

import androidx.paging.PagingSource
import com.punnales.moviesapp.core.domain.Movie
import com.punnales.moviesapp.core.domain.ResourceRoute
import com.punnales.moviesapp.data.local.room.daos.MovieDao
import com.punnales.moviesapp.data.local.room.dto.MovieDTO
import com.punnales.moviesapp.data.local.room.dto.ResourceRouteDTO
import com.punnales.moviesapp.data.local.room.mappers.fromDatabase
import com.punnales.moviesapp.data.local.room.mappers.toDatabase
import com.punnales.moviesapp.data.remote.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieLocalDataSourceImpl @Inject constructor(private val movieDao: MovieDao) : MovieLocalDataSource {

    override suspend fun addMovieList(movieList: List<Movie>) =
        getResponse { movieDao.insertMovieList(movieList.map(Movie::toDatabase)) }

    override fun getMovieList(): PagingSource<Int, MovieDTO> =
        movieDao.getPagedMovies()

    override suspend fun addResourceRouteList(resourceRouteList: List<ResourceRoute>): Resource<Unit> =
        getResponse { movieDao.insertResourceRouteList(resourceRouteList.map { it.toDatabase() }) }

    override suspend fun getResourceRouteList(): Flow<List<ResourceRoute>> =
        movieDao.getResourceRouteList().map { it.map(ResourceRouteDTO::fromDatabase) }
}