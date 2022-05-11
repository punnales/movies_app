package com.punnales.moviesapp.data.local.room.di

import android.content.Context
import androidx.room.Room
import com.punnales.moviesapp.data.local.room.MovieAppDatabase
import com.punnales.moviesapp.data.local.room.daos.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMovieAppDatabase(@ApplicationContext applicationContext: Context): MovieAppDatabase {
        return Room.databaseBuilder(
            applicationContext,
            MovieAppDatabase::class.java,
            MovieAppDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideMovieDao(movieAppDatabase: MovieAppDatabase) = movieAppDatabase.movieDao()
}