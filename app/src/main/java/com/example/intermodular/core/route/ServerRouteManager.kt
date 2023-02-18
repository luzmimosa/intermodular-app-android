package com.example.intermodular.core.route

import com.example.intermodular.core.network.RetrofitHelper
import com.example.intermodular.core.route.client.NearRoutesClient
import com.example.intermodular.core.route.client.RandomRoutesClient
import com.example.intermodular.core.route.client.RouteClient
import com.example.intermodular.core.route.client.RouteUploadClient
import com.example.intermodular.core.route.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.ZoneOffset

object ServerRouteManager {

    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getRouteByID(routeID: String): Route? {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(RouteClient::class.java).getRoute(routeID)

            if (!response.isSuccessful) return@withContext null

            val routeResponse = response.body() ?: return@withContext null

            val locations: Array<ServerGpsMeasure> = routeResponse.measures.map { measure ->
                val waypoint: ServerWaypoint? = if (measure.waypoint == null) null else ServerWaypoint(measure.waypoint.name, measure.waypoint.description, measure.waypoint.imagePath)
                return@map ServerGpsMeasure(measure.latitude, measure.longitude, waypoint)
            }.toTypedArray()
            val types: Array<RouteType> = if (routeResponse.types == null) arrayOf() else routeResponse.types.map { type ->
                try {
                    return@map RouteType.valueOf(type)
                } catch (exception: java.lang.IllegalArgumentException) {
                    return@map null
                }
            }.filterNotNull().toTypedArray()

            val difficulty: RouteDifficulty = try {
                RouteDifficulty.valueOf(routeResponse.difficulty)
            } catch (exception: java.lang.IllegalArgumentException) {
                RouteDifficulty.MEDIUM
            }


            return@withContext ServerRoute(
                uid = routeResponse.uid,
                name = routeResponse.name,
                description = routeResponse.description,
                imageID = routeResponse.imagePath,
                lengthInKm = routeResponse.length,
                locations = locations,
                types = types,
                difficulty = difficulty,
                creator = routeResponse.creator,
                creationDatetime = LocalDateTime.ofEpochSecond(routeResponse.creationDatetime / 1000, 0, ZoneOffset.UTC),
                likes = routeResponse.likes
            ).asRoute()
        }
    }

    suspend fun getRoutesByLocation(latitude: Double, longitude: Double, radius: Int = 30, limit: Int = 20): Array<Route> {
        try {
            return withContext(Dispatchers.IO) {

                val response = retrofit.create(NearRoutesClient::class.java).getNearRoutes(latitude, longitude, radius)
                if (!response.isSuccessful) return@withContext arrayOf()

                val routeIdsResponse = response.body() ?: return@withContext arrayOf()

                val routes = mutableListOf<Route>()

                for (routeID in routeIdsResponse) {
                    val route = getRouteByID(routeID)
                    if (route != null && routes.size < limit) {
                        routes.add(route)
                    }
                }

                return@withContext routes.toTypedArray()
            }
        } catch (exception: Exception) {
            return arrayOf()
        }
    }

    suspend fun getRandomRoutes(max: Int = 10): Array<Route> {
        try {
            return withContext(Dispatchers.IO) {
                val response = retrofit.create(RandomRoutesClient::class.java).getRandomRoutes()
                if (!response.isSuccessful) return@withContext arrayOf()

                val routeIdsResponse = response.body() ?: return@withContext arrayOf()

                val routes = mutableListOf<Route>()

                for (routeID in routeIdsResponse) {
                    val route = getRouteByID(routeID)
                    if (route != null && routes.size < max) {
                        routes.add(route)
                    }
                }

                return@withContext routes.toTypedArray()
            }
        } catch (exception: Exception) {
            return arrayOf()
        }
    }

    suspend fun uploadRoute(route: UploadableRoute): Boolean {
        try {
            return withContext(Dispatchers.IO) {
                val response = retrofit.create(RouteUploadClient::class.java).uploadRoute(route)
                return@withContext response.isSuccessful
            }
        } catch (exception: Exception) {
            return false
        }
    }
}