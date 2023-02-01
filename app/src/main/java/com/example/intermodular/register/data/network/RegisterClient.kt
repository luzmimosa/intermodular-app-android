package com.example.intermodular.register.data.network

import com.example.intermodular.login.data.network.response.LoginResponse
import retrofit2.Response
import retrofit2.http.GET

interface RegisterClient {
    @GET("/v3/4fdefa5a-ee25-439b-b32f-ac7e698b71fd")
    suspend fun doRegister(): Response<LoginResponse>
}