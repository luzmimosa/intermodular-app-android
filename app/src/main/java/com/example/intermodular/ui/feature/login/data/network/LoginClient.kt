package com.example.intermodular.ui.feature.login.data.network

import com.example.intermodular.ui.feature.login.data.dto.LoginDTO
import com.example.intermodular.ui.feature.login.data.network.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginClient {
    @POST("/account/login")
    suspend fun doLogin(
        @Body data: LoginDTO
    ): Response<LoginResponse>
}