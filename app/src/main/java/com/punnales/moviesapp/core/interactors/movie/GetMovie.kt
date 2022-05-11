package com.punnales.moviesapp.core.interactors.movie

import com.punnales.moviesapp.core.domain.Movie
import com.punnales.moviesapp.data.local.room.datasource.MovieLocalDataSource
import com.punnales.moviesapp.data.remote.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetMovie @Inject constructor(private val movieLocalDataSource: MovieLocalDataSource) {

    sealed class GetMovieResult {
        object Loading : GetMovieResult()
        class Success(val movie: Movie) : GetMovieResult()
    }

    suspend operator fun invoke(movieId: Long): Flow<GetMovieResult> {
        return withContext(Dispatchers.IO) {
            return@withContext flow {
                emit(GetMovieResult.Loading)
                when (val result = movieLocalDataSource.getMovie(movieId)) {
                    is Resource.Error -> {}
                    is Resource.Success -> emit(GetMovieResult.Success(result.data))
                }
            }
        }
    }
}