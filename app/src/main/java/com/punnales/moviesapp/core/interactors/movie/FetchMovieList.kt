package com.punnales.moviesapp.core.interactors.movie

import com.punnales.moviesapp.core.domain.Movie
import com.punnales.moviesapp.core.domain.ResourceRoute
import com.punnales.moviesapp.data.remote.datasource.MovieRemoteDataSource
import com.punnales.moviesapp.data.remote.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchMovieList @Inject constructor(private val movieRemoteDataSource: MovieRemoteDataSource) {

    sealed class FetchMovieListResult {
        object Loading : FetchMovieListResult()
        class Success(val movieListPair: Pair<List<Movie>, List<ResourceRoute>>) : FetchMovieListResult()
        object ConnectionError : FetchMovieListResult()
    }

    suspend operator fun invoke(): Flow<FetchMovieListResult> {
        return withContext(Dispatchers.IO) {
            return@withContext flow {
                emit(FetchMovieListResult.Loading)
                when (val result = movieRemoteDataSource.fetchMovieList()) {
                    is Resource.Error -> emit(FetchMovieListResult.ConnectionError)
                    is Resource.Success -> emit(FetchMovieListResult.Success(result.data))
                }
            }
        }
    }
}