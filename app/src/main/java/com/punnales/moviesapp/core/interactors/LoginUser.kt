package com.punnales.moviesapp.core.interactors

import com.punnales.moviesapp.core.domain.User
import com.punnales.moviesapp.data.remote.datasource.UserRemoteDataSource
import com.punnales.moviesapp.data.remote.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.net.UnknownHostException
import javax.inject.Inject

class LoginUser @Inject constructor(private val userRemoteDataSource: UserRemoteDataSource) {

    sealed class LoginUserResult {
        object Loading : LoginUserResult()
        class Success(val user: User) : LoginUserResult()
        object ConnectionError : LoginUserResult()
        object WrongCredentialsError : LoginUserResult()
    }

    suspend operator fun invoke(userName: String, password: String): Flow<LoginUserResult> {
        return withContext(Dispatchers.IO) {
            return@withContext flow {
                emit(LoginUserResult.Loading)
                when (val loginResult = userRemoteDataSource.login(userName, password)) {
                    is Resource.Error -> emit(getError(loginResult.exception))
                    is Resource.Success -> emit(LoginUserResult.Success(loginResult.data))
                }
            }
        }
    }

    private fun getError(exception: Exception): LoginUserResult =
        when (exception) {
            is UnknownHostException -> LoginUserResult.ConnectionError
            else -> LoginUserResult.WrongCredentialsError
        }
}