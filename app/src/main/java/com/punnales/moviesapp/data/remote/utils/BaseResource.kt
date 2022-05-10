package com.punnales.moviesapp.data.remote.utils

interface BaseDataSource {
    suspend fun <T> getResponse(
        request: suspend () -> T,
    ): Resource<T> {
        return try {
            Resource.Success(request.invoke())
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val exception: Exception) : Resource<Nothing>()
}