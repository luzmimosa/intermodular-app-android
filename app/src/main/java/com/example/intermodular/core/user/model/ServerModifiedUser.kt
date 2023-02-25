package com.example.intermodular.core.user.model

import com.google.gson.annotations.SerializedName

data class ServerModifiedUser(
    @SerializedName("displayName")      val displayName: String,
    @SerializedName("biography")        val biography: String,
    @SerializedName("profilePicture")   val profilePicture: String
)