package com.example.intermodular.core.image.client

import com.example.intermodular.core.image.response.RouteIdResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ImageUploadClient {

    @Multipart
    @POST("/image")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part
    ): Response<RouteIdResponse>
}