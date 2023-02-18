package com.example.intermodular.core.image.client

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ImageDownloadClient {

    @GET("/image/{routeId}")
    suspend fun downloadImage(
        @Path("routeId") routeId: String
    ): Response<ResponseBody>

}