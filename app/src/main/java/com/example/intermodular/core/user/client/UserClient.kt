package com.example.intermodular.core.user.client

import com.example.intermodular.core.user.model.ServerModifiedUser
import com.example.intermodular.core.user.model.ServerUser
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserClient {

    @GET("/api/v1/user/{username}")
    suspend fun getUserByUsername(@Path("username")username: String): Response<ServerUser>

    @GET("/api/v1/user")
    suspend fun getSelfUser(): Response<ServerUser>

    @POST("/account/modify")
    suspend fun modifySelfUser(
        @Body user: ServerModifiedUser
    ): Response<Unit>

}