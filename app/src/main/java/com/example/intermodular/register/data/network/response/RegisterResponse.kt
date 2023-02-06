package com.example.intermodular.register.data.network.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("message")
    val message: String,
    val success: Boolean = message == "SUCCESS"
)