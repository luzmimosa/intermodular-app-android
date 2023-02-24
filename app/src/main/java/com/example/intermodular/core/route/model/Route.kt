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
        return if (other is Route) {
            uid == other.uid
        } else {
            false
        }
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

