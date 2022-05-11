package com.punnales.moviesapp.core.interactors.user

import com.punnales.moviesapp.core.domain.User
import com.punnales.moviesapp.data.remote.datasource.UserRemoteDataSource
import com.punnales.moviesapp.data.remote.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchUserProfile @Inject constructor(private val userRemoteDataSource: UserRemoteDataSource) {

    sealed class FetchUserProfileResult {
        object Loading : FetchUserProfileResult()
        class Success(val user: User) : FetchUserProfileResult()
        object ConnectionError : FetchUserProfileResult()
    }

    suspend operator fun invoke(accessToken: String): Flow<FetchUserProfileResult> {
        return withContext(Dispatchers.IO) {
            return@withContext flow {
                emit(FetchUserProfileResult.Loading)
                when (val result = userRemoteDataSource.fetchUserProfile(accessToken)) {
                    is Resource.Error -> emit(FetchUserProfileResult.ConnectionError)
                    is Resource.Success -> emit(FetchUserProfileResult.Success(result.data))
                }
            }
        }
    }
}