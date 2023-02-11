package com.example.intermodular.ui.feature.login.data.network.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("message") val message: String
)