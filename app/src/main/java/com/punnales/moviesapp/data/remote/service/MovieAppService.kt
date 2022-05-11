package com.punnales.moviesapp.data.remote.service

import com.punnales.moviesapp.BuildConfig
import com.punnales.moviesapp.data.remote.dto.request.TransactionRequestDTO
import com.punnales.moviesapp.data.remote.dto.response.LoginResponseDTO
import com.punnales.moviesapp.data.remote.dto.response.MovieListResponseDTO
import com.punnales.moviesapp.data.remote.dto.response.TransactionListResponseDTO
import com.punnales.moviesapp.data.remote.dto.response.UserResponseDTO
import com.punnales.moviesapp.data.remote.utils.ENDPOINT_LOGIN
import com.punnales.moviesapp.data.remote.utils.ENDPOINT_TRANSACTIONS
import com.punnales.moviesapp.data.remote.utils.ENDPOINT_MOVIES
import com.punnales.moviesapp.data.remote.utils.ENDPOINT_USER_PROFILE
import retrofit2.http.*

/**
 * Only one service created for the sake of simplicity and the project size.
 */
interface MovieAppService {

    @FormUrlEncoded
    @POST(ENDPOINT_LOGIN)
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("country_code") country_code: String = "MX",
        @Field("grant_type") grantType: String = "password",
        @Field("client_id") clientId: String = BuildConfig.CLIENT_ID,
        @Field("client_secret") client_secret: String = BuildConfig.CLIENT_SECRET,
    ): LoginResponseDTO

    @GET(ENDPOINT_USER_PROFILE)
    suspend fun fetchUserProfile(@Header("Authorization") accessToken: String): UserResponseDTO

    @GET(ENDPOINT_MOVIES)
    suspend fun fetchMovieList(): MovieListResponseDTO

    @POST(ENDPOINT_TRANSACTIONS)
    suspend fun fetchTransactionList(@Body transactionRequestDTO: TransactionRequestDTO): TransactionListResponseDTO

}