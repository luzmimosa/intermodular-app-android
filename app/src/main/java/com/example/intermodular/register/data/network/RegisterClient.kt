package com.example.intermodular.register.data.network

import com.example.intermodular.register.data.network.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterClient {
    @POST("/account/register")
    suspend fun doRegister(
        @Body displayName : String,
        @Body username: String,
        @Body email : String,
        @Body password : String,
        @Body biography: String = ":("
    ): Response<RegisterResponse>
}