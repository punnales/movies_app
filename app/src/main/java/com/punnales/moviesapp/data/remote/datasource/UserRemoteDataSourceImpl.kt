package com.punnales.moviesapp.data.remote.datasource

import com.punnales.moviesapp.core.domain.Transaction
import com.punnales.moviesapp.core.domain.User
import com.punnales.moviesapp.data.remote.dto.request.TransactionRequestDTO
import com.punnales.moviesapp.data.remote.mappers.fromRemote
import com.punnales.moviesapp.data.remote.service.MovieAppService
import com.punnales.moviesapp.data.remote.utils.Resource
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(private val movieAppService: MovieAppService) : UserRemoteDataSource {

    override suspend fun login(userName: String, password: String) =
        getResponse { movieAppService.login(userName, password).fromRemote() }

    override suspend fun fetchUserProfile(accessToken: String): Resource<User> =
        getResponse { movieAppService.fetchUserProfile(accessToken).fromRemote() }

    override suspend fun fetchTransactionList(cardNumber: String): Resource<List<Transaction>> =
        getResponse { movieAppService.fetchTransactionList(TransactionRequestDTO(cardNumber)).transactionList.map { it.fromRemote() } }
}