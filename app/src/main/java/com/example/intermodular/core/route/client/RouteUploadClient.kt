package com.example.intermodular.core.route.client

import com.example.intermodular.core.route.client.response.MessageResponse
import com.example.intermodular.core.route.model.UploadableRoute
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RouteUploadClient {

    @POST("/api/v1/route/create")
    suspend fun uploadRoute(
        @Body route: UploadableRoute
    ): Response<MessageResponse>

}