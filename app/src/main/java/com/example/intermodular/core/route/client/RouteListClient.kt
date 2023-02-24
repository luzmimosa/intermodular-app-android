package com.example.intermodular.core.route.client

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RouteListClient {

    @GET("/api/v1/userroutes/{username}")
    suspend fun getUserRoutes(@Path("username") username: String): Response<Array<String>>

    @GET("/api/v1/nearlyroutes/{latitude}/{longitude}/{radius}/{radius}")
    suspend fun getNearRoutes(
        @Path("latitude") latitude: Double,
        @Path("longitude") longitude: Double,
        @Path("radius") radius: Int = 30
    ): Response<Array<String>>

    @GET("/api/v1/randomroutes")
    suspend fun getRandomRoutes(): Response<Array<String>>

}