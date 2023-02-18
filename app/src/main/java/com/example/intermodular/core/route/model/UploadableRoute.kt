package com.example.intermodular.core.route.model

import com.google.gson.annotations.SerializedName

data class UploadableRoute(

    @SerializedName("name")             val name: String,
    @SerializedName("description")      val description: String,
    @SerializedName("difficulty")       val difficulty: String,
    @SerializedName("image")            val image: String,
    @SerializedName("types")            val types: List<String>,
    @SerializedName("locations")        val locations: List<UploadableLocation>

)

data class UploadableLocation(

    @SerializedName("latitude")         val latitude: Double,
    @SerializedName("longitude")        val longitude: Double,
    @SerializedName("waypoint")         val waypoint: UploadableWaypoint?,

)

data class UploadableWaypoint(

    @SerializedName("name")             val name: String,
    @SerializedName("description")      val description: String,
    @SerializedName("image")            val image: String,

)