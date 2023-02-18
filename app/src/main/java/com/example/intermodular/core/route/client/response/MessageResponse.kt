package com.example.intermodular.core.route.client.response

import com.google.gson.annotations.SerializedName

data class MessageResponse(
    @SerializedName("message")  val message: String
)