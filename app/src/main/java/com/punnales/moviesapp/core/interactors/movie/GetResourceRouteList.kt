package com.punnales.moviesapp.core.interactors.movie

import com.punnales.moviesapp.data.local.room.datasource.MovieLocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetResourceRouteList @Inject constructor(private val movieLocalDataSource: MovieLocalDataSource) {

    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        movieLocalDataSource.getResourceRouteList()
    }
}