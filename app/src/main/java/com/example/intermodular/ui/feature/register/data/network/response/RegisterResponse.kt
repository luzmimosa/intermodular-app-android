package com.example.intermodular.ui.feature.register.data.network.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("message")
    val message: String,
    val success: Boolean = message == "SUCCESS"
)