package com.example.intermodular.core.route.client

import com.example.intermodular.core.route.model.UploadableComment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface CommentRouteClient {

    @POST("/api/v1/comment/{routeID}")
    suspend fun commentRoute(
        @Path("routeID") routeID: String,
        @Body comment: UploadableComment
    ) : Response<Unit>

}