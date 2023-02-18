package com.example.intermodular.core.route.model

import android.util.Log
import com.example.intermodular.core.image.ServerImageManager
import java.time.LocalDateTime

data class ServerRoute(
    val uid: String,
    val name: String,
    val description: String,
    val imageID: String,
    val lengthInKm: Double,

    val locations: Array<ServerGpsMeasure>,

    val types: Array<RouteType>,
    val difficulty: RouteDifficulty,

    val creator: String,
    val creationDatetime: LocalDateTime,

    val likes: Int = 0,

    ) {
    override fun toString(): String {
        return "Route(uid='$uid', name='$name', description='$description', imageID='$imageID', lengthInKm=$lengthInKm, locations=${locations.contentToString()}, types=${types.contentToString()}, difficulty=$difficulty, creator='$creator', creationDatetime=$creationDatetime)"
    }

    suspend fun asRoute(): Route {
        Log.i("ServerRoute", "asRoute: $this")

        val image = ServerImageManager.getImage(this.imageID)
        if (image == null) {
            Log.e("ServerRoute", "asRoute: image is null")
            throw Exception("Image is null")
        }

        val route = Route(
            uid = this.uid,
            name = this.name,
            description = this.description,
            image = image,
            lengthInKm = this.lengthInKm,
            locations = this.locations.map { measure ->
                val waypoint: Waypoint? = if (measure.waypoint == null) null else Waypoint(measure.waypoint.name, measure.waypoint.description, measure.waypoint.imageID?.let {
                    ServerImageManager.getImage(it)
                }!!)
                return@map GpsMeasure(measure.latitude, measure.longitude, waypoint)
            }.toTypedArray(),
            types = this.types,
            difficulty = this.difficulty,
            creator = this.creator,
            creationDatetime = this.creationDatetime,
            likes = this.likes
        )


        Log.i("ServerRoute", "asRoute done!")

        return route
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

data class ServerGpsMeasure(
    val latitude: Double,
    val longitude: Double,
    val waypoint: ServerWaypoint? = null
)

data class ServerWaypoint(
    val name: String,
    val description: String,
    val imageID: String? = null
)