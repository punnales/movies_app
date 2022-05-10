package com.punnales.moviesapp.data.remote.service

import com.punnales.moviesapp.BuildConfig
import com.punnales.moviesapp.data.remote.dto.LoginResponseDTO
import com.punnales.moviesapp.data.remote.utils.ENDPOINT_LOGIN
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

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

}