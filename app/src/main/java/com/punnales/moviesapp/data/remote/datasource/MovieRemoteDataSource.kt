package com.punnales.moviesapp.data.remote.datasource

import com.punnales.moviesapp.core.domain.Movie
import com.punnales.moviesapp.core.domain.ResourceRoute
import com.punnales.moviesapp.data.remote.utils.BaseDataSource
import com.punnales.moviesapp.data.remote.utils.Resource

interface MovieRemoteDataSource : BaseDataSource {

    suspend fun fetchMovieList(): Resource<Pair<List<Movie>, List<ResourceRoute>>>
}