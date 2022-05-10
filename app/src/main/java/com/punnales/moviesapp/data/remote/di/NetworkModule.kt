package com.punnales.moviesapp.data.remote.di

import com.punnales.moviesapp.BuildConfig
import com.punnales.moviesapp.data.remote.service.MovieAppService
import com.punnales.moviesapp.data.remote.utils.API_KEY_HEADER_NAME
import com.punnales.moviesapp.data.remote.utils.BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val READ_TIME_OUT = 20L
    private const val WRITE_TIME_OUT = 20L
    private const val CONNECTION_TIME_OUT = 20L

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
            .connectTimeout(CONNECTION_TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor {
                it.proceed(it.request()) //TODO Debug purposes, delete before delivery
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory()).build()))
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideDriverApiService(retrofit: Retrofit): MovieAppService {
        return retrofit.create(MovieAppService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(): Interceptor =
        Interceptor { chain ->
            chain.proceed(chain.request().newBuilder().addHeader(API_KEY_HEADER_NAME, BuildConfig.API_KEY).build())
        }
}