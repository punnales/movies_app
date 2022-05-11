package com.punnales.moviesapp.core.interactors.user

import com.punnales.moviesapp.core.domain.Transaction
import com.punnales.moviesapp.data.remote.datasource.UserRemoteDataSource
import com.punnales.moviesapp.data.remote.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.net.UnknownHostException
import javax.inject.Inject

class FetchTransactionList @Inject constructor(private val userRemoteDataSource: UserRemoteDataSource) {

    sealed class FetchTransactions {
        object Loading : FetchTransactions()
        class Success(val listTransactions: List<Transaction>) : FetchTransactions()
        object ConnectionError : FetchTransactions()
        object UserNotFoundError : FetchTransactions()
    }

    suspend operator fun invoke(cardNumber: String): Flow<FetchTransactions> {
        return withContext(Dispatchers.IO) {
            return@withContext flow {
                emit(FetchTransactions.Loading)
                when (val result = userRemoteDataSource.fetchTransactionList(cardNumber)) {
                    is Resource.Error -> emit(getError(result.exception))
                    is Resource.Success -> emit(FetchTransactions.Success(result.data))
                }
            }
        }
    }

    private fun getError(exception: Exception): FetchTransactions =
        if (exception is UnknownHostException)
            FetchTransactions.ConnectionError
        else
            FetchTransactions.UserNotFoundError
}