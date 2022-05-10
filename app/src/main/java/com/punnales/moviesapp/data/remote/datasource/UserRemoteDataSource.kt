package com.punnales.moviesapp.data.remote.datasource

import com.punnales.moviesapp.core.domain.User
import com.punnales.moviesapp.data.remote.dto.LoginResponseDTO
import com.punnales.moviesapp.data.remote.utils.BaseDataSource
import com.punnales.moviesapp.data.remote.utils.Resource
import javax.inject.Inject

interface UserRemoteDataSource : BaseDataSource {

    suspend fun login(userName: String, password: String): Resource<User>

}