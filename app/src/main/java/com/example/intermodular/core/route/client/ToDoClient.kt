package com.example.intermodular.core.route.client

import retrofit2.Response
import retrofit2.http.PUT
import retrofit2.http.Path

interface ToDoClient {

    @PUT("/todo/{routeID}")
    suspend fun add(@Path("routeID") routeID: String): Response<Unit>

    @PUT("/todo/{routeID}")
    suspend fun remove(@Path("routeID") routeID: String): Response<Unit>

}