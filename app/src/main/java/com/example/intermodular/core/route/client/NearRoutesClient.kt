package com.example.intermodular.core.route.client

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface NearRoutesClient {

    @GET("/api/v1/nearlyroutes/{latitude}/{longitude}/{radius}/{radius}")
    suspend fun getNearRoutes(
        @Path("latitude") latitude: Double,
        @Path("longitude") longitude: Double,
        @Path("radius") radius: Int
    ): Response<Array<String>>

}