package com.example.intermodular.core.route.client

import com.example.intermodular.core.route.client.single.RouteResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RouteClient {

    @GET("/api/v1/route/{routeID}")
    suspend fun getRoute(@Path("routeID") routeID: String): Response<RouteResponse>
}