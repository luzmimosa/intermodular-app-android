package com.example.intermodular.core.route.model

import java.time.LocalDateTime

data class Route(
    val uid: String,
    val name: String,
    val description: String,
    val imageID: String,
    val lengthInKm: Double,

    val locations: Array<GpsMeasure>,

    val types: Array<RouteType>,
    val difficulty: RouteDifficulty,

    val creator: String,
    val creationDatetime: LocalDateTime,

    val likes: Int = 0,

) {
    override fun toString(): String {
        return "Route(uid='$uid', name='$name', description='$description', imageID='$imageID', lengthInKm=$lengthInKm, locations=${locations.contentToString()}, types=${types.contentToString()}, difficulty=$difficulty, creator='$creator', creationDatetime=$creationDatetime)"
    }
}

enum class RouteType {
    WALK,
    TREKKING,
    RUNNING,
    BYCICLE,
    PHOTOGRAPHY
}

enum class RouteDifficulty {
    TRIVIAL,
    EASY,
    MEDIUM,
    HARD,
    EXPERT
}

data class GpsMeasure(
    val latitude: Double,
    val longitude: Double,
    val waypoints: Waypoint? = null
)

data class Waypoint(
    val name: String,
    val description: String,
    val imageID: String? = null
)