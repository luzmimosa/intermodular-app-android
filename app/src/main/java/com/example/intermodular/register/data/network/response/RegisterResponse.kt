package com.example.intermodular.register.data.network.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("ok")
    val registerOk: Boolean
)