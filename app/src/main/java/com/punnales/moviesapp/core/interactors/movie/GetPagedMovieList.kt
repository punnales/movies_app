package com.punnales.moviesapp.core.interactors.movie

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.punnales.moviesapp.data.local.room.datasource.MovieLocalDataSource
import com.punnales.moviesapp.data.local.room.dto.MovieDTO
import javax.inject.Inject

class GetPagedMovieList @Inject constructor(private val movieLocalDataSource: MovieLocalDataSource) {

    companion object {
        private const val PAGE_SIZE = 60
        private const val PREFETCH_DISTANCE = 40
    }

    operator fun invoke(): Pager<Int, MovieDTO> {
        return Pager(PagingConfig(PAGE_SIZE, PREFETCH_DISTANCE, false)) {
            movieLocalDataSource.getMovieList()
        }
    }
}