package com.example.intermodular.core.route.client.response

import com.google.gson.annotations.SerializedName

data class RouteResponse (
    @SerializedName("uid")              val uid: String,
    @SerializedName("name")             val name: String,
    @SerializedName("description")      val description: String,
    @SerializedName("image")            val imagePath: String,
    @SerializedName("startingLocation") val startingLocation: RouteResponseLocation,
    @SerializedName("locations")        val measures: Array<RouteResponseLocation>,
    @SerializedName("types")            val types: Array<String>?,
    @SerializedName("length")           val length: Double,
    @SerializedName("difficulty")       val difficulty: String,
    @SerializedName("creator")          val creator: String,
    @SerializedName("creationDatetime") val creationDatetime: Long,
    @SerializedName("likes")            val likes: Int,
    @SerializedName("comments")         val comments: Array<RouteResponseComment>
)

data class RouteResponseLocation (
    @SerializedName("latitude")         val latitude: Double,
    @SerializedName("longitude")        val longitude: Double,
    @SerializedName("waypoint")         val waypoint: RouteResponseWaypoint?
)

data class RouteResponseWaypoint(
    @SerializedName("name")             val name: String,
    @SerializedName("description")      val description: String,
    @SerializedName("image")            val imagePath: String?
)

data class RouteResponseComment(
    @SerializedName("username")         val username: String,
    @SerializedName("comment")          val comment: String,
    @SerializedName("date")             val date: Long,
)