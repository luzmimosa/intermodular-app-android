package com.example.intermodular.core.route.client.single

import com.google.gson.annotations.SerializedName

data class RouteResponse (
    @SerializedName("uid")              val uid: String,
    @SerializedName("name")             val name: String,
    @SerializedName("description")      val description: String,
    @SerializedName("image")            val imagePath: String,
    @SerializedName("startingLocation") val startingLocation: Location,
    @SerializedName("locations")        val measures: Array<Location>,
    @SerializedName("types")            val types: Array<String>?,
    @SerializedName("length")           val length: Double,
    @SerializedName("difficulty")       val difficulty: String,
    @SerializedName("creator")          val creator: String,
    @SerializedName("creationDatetime") val creationDatetime: Long
)

data class Location (
    @SerializedName("latitude")         val latitude: Float,
    @SerializedName("longitude")        val longitude: Float,
    @SerializedName("waypoint")         val waypoint: Waypoint?
)

data class Waypoint(
    @SerializedName("name")             val name: String,
    @SerializedName("description")      val description: String,
    @SerializedName("image")            val imagePath: String?
)