package com.punnales.moviesapp.data.remote.datasource

import com.punnales.moviesapp.core.domain.Movie
import com.punnales.moviesapp.core.domain.ResourceRoute
import com.punnales.moviesapp.data.remote.mappers.fromRemote
import com.punnales.moviesapp.data.remote.service.MovieAppService
import com.punnales.moviesapp.data.remote.utils.BaseDataSource
import com.punnales.moviesapp.data.remote.utils.Resource
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(private val movieAppService: MovieAppService) : MovieRemoteDataSource {

    override suspend fun fetchMovieList(): Resource<Pair<List<Movie>, List<ResourceRoute>>> =
        getResponse { movieAppService.fetchMovieList().fromRemote() }
}