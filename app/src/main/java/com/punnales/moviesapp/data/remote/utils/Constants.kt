package com.punnales.moviesapp.data.remote.utils

internal const val BASE_URL = "https://stage-api.cinepolis.com"
internal const val API_KEY_HEADER_NAME = "api_key"

//Endpoints
internal const val ENDPOINT_LOGIN = "/v2/oauth/token"
internal const val ENDPOINT_USER_PROFILE = "/v1/members/profile?country_code=MX"
internal const val ENDPOINT_LOYALTY = "/v1/members/loyalty"
internal const val ENDPOINT_MOVIES = "/v2/movies?country_code=MX&cinema=61"