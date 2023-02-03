package com.example.intermodular.register.data.network

import com.example.intermodular.register.data.dto.UserDTO
import com.example.intermodular.register.data.network.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterClient {
    @POST("/account/register")
    suspend fun doRegister(
        @Body data: UserDTO
    ): Response<RegisterResponse>
}