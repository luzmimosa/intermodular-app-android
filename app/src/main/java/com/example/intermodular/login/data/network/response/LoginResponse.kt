package com.example.intermodular.login.data.network.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("message")
    val loginResponse: String
)