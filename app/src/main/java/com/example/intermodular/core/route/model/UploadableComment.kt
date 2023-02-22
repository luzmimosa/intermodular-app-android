package com.example.intermodular.core.route.model

import com.google.gson.annotations.SerializedName

data class UploadableComment(
    @SerializedName("comment")  val comment: String
)
