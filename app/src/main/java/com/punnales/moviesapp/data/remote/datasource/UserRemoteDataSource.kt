package com.punnales.moviesapp.data.remote.datasource

import com.punnales.moviesapp.core.domain.Transaction
import com.punnales.moviesapp.core.domain.User
import com.punnales.moviesapp.data.remote.utils.BaseDataSource
import com.punnales.moviesapp.data.remote.utils.Resource

interface UserRemoteDataSource : BaseDataSource {

    suspend fun login(userName: String, password: String): Resource<User>

    suspend fun fetchUserProfile(accessToken: String): Resource<User>

    suspend fun fetchTransactionList(cardNumber: String): Resource<List<Transaction>>

}