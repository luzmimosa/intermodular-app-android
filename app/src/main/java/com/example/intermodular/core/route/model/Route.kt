package com.example.intermodular.core.route.model

import androidx.compose.ui.graphics.ImageBitmap
import java.time.LocalDateTime

data class Route(
    val uid: String,
    val name: String,
    val description: String,
    val image: ImageBitmap,
    val lengthInKm: Double,

    val locations: Array<GpsMeasure>,

    val types: Array<RouteType>,
    val difficulty: RouteDifficulty,

    val creator: String,
    val creationDatetime: LocalDateTime,

    val likes: Int = 0,

    val comments: Array<Comment> = arrayOf()


) {
    override fun equals(other: Any?): Boolean {
        if (other !is Route) return false
        if (other.uid != uid) return false
        if (other.name != name) return false
        if (other.description != description) return false
        if (other.image != image) return false
        if (other.lengthInKm != lengthInKm) return false
        if (!other.locations.contentEquals(locations)) return false
        if (!other.types.contentEquals(types)) return false
        if (other.difficulty != difficulty) return false
        if (other.creator != creator) return false
        if (other.creationDatetime != creationDatetime) return false
        if (other.likes != likes) return false
        if (!other.comments.contentEquals(comments)) return false
        return true
    }
}

data class GpsMeasure(
    val latitude: Double,
    val longitude: Double,
    val waypoint: Waypoint? = null
)

data class Waypoint(
    val name: String,
    val description: String,
    val image: ImageBitmap
)

data class Comment(
    val username: String,
    val comment: String,
    val datetime: LocalDateTime
)

