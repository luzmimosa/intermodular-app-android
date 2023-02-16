package com.example.intermodular.core.route.client

import retrofit2.Response
import retrofit2.http.GET

interface RandomRoutesClient {

    @GET("/api/v1/randomroutes")
    suspend fun getRandomRoutes(): Response<Array<String>>

}
