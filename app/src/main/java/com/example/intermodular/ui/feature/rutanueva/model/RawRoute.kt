package com.example.intermodular.ui.feature.rutanueva.model

import androidx.compose.ui.graphics.ImageBitmap
import com.example.intermodular.core.image.ServerImageManager
import com.example.intermodular.core.location.LocationMark
import com.example.intermodular.core.route.model.*

data class RawRoute(
    val name: String,
    val description: String,
    val difficulty: RouteDifficulty,
    val types: List<RouteType>,
    val image: ImageBitmap,
    val locations: List<LocationMark>,
    val waypoints: List<RawWaypoint>
) {
    suspend fun asUploadableRoute(): UploadableRoute {

        val uploadableLocations = mutableListOf<UploadableLocation>()

        for (location in this.locations) {
            val waypoint: RawWaypoint? = this.waypoints.find { it.latitude == location.latitude && it.longitude == location.longitude }
            var uploadableWaypoint: UploadableWaypoint? = null

            if (waypoint != null) {
                uploadableWaypoint = UploadableWaypoint(
                    name = waypoint.name,
                    description = waypoint.description,
                    image = ServerImageManager.uploadImage(waypoint.image)
                )
            }

            uploadableLocations.add(
                UploadableLocation(
                    latitude = location.latitude,
                    longitude = location.longitude,
                    waypoint = uploadableWaypoint
                )
            )
        }

        val mainImage = ServerImageManager.uploadImage(this.image)

        return UploadableRoute(
            name = this.name,
            description = this.description,
            types = this.types.map { it.name },
            difficulty = this.difficulty.name,
            image = mainImage,
            locations = uploadableLocations
        )
    }
}

data class RawWaypoint(
    val name: String,
    val description: String,
    val latitude: Double,
    val longitude: Double,
    val image: ImageBitmap
)

