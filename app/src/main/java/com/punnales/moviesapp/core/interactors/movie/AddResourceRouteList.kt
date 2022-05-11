package com.punnales.moviesapp.core.interactors.movie

import android.util.Log
import com.punnales.moviesapp.core.domain.Movie
import com.punnales.moviesapp.core.domain.ResourceRoute
import com.punnales.moviesapp.data.local.room.datasource.MovieLocalDataSource
import com.punnales.moviesapp.data.remote.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddResourceRouteList @Inject constructor(private val movieLocalDataSource: MovieLocalDataSource) {

    sealed class AddMovieListResult {
        object Success : AddMovieListResult()
        object Error : AddMovieListResult()
    }

    suspend operator fun invoke(resourceRouteList: List<ResourceRoute>): Flow<AddMovieListResult> {
        return withContext(Dispatchers.IO) {
            return@withContext flow {
                when (val result = movieLocalDataSource.addResourceRouteList(resourceRouteList)) {
                    is Resource.Error -> {
                        emit(AddMovieListResult.Error)
                    }
                    is Resource.Success -> emit(AddMovieListResult.Success)
                }
            }
        }
    }
}