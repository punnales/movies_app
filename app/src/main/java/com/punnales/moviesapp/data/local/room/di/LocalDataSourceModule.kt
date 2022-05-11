package com.punnales.moviesapp.data.local.room.di

import com.punnales.moviesapp.data.local.room.datasource.MovieLocalDataSource
import com.punnales.moviesapp.data.local.room.datasource.MovieLocalDataSourceImpl
import com.punnales.moviesapp.data.remote.datasource.MovieRemoteDataSource
import com.punnales.moviesapp.data.remote.datasource.MovieRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {

    @Binds
    abstract fun bindMovieLocalDataSource(movieLocalDataSourceImpl: MovieLocalDataSourceImpl): MovieLocalDataSource

}