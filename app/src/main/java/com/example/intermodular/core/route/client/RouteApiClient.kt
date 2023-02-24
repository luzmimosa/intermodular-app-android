package com.example.intermodular.core.route.client

import com.example.intermodular.core.route.model.UploadableComment
import retrofit2.Response
import retrofit2.http.*

interface RouteApiClient {

    @POST("/api/v1/comment/{routeID}")
    suspend fun commentRoute(
        @Path("routeID") routeID: String,
        @Body comment: UploadableComment
    ) : Response<Unit>

    @PUT("/like/{routeID}")
    suspend fun likeRoute(@Path("routeID") routeID: String): Response<Unit>

    @DELETE("/like/{routeID}")
    suspend fun unlikeRoute(@Path("routeID") routeID: String): Response<Unit>

    @PUT("/todo/{routeID}")
    suspend fun add(@Path("routeID") routeID: String): Response<Unit>

    @PUT("/todo/{routeID}")
    suspend fun remove(@Path("routeID") routeID: String): Response<Unit>


}