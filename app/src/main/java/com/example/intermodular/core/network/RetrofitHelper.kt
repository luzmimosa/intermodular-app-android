package com.example.intermodular.core.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    fun getRetrofit(): Retrofit{
        return Retrofit.Builder().baseUrl("https://intermodular.fadedbytes.com")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
}