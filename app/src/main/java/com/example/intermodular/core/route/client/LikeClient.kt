package com.example.intermodular.core.route.client

import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.PUT
import retrofit2.http.Path

interface LikeClient {

    @PUT("/like/{routeID}")
    suspend fun likeRoute(@Path("routeID") routeID: String): Response<Unit>

    @DELETE("/like/{routeID}")
    suspend fun unlikeRoute(@Path("routeID") routeID: String): Response<Unit>

}