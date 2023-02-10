package com.example.intermodular.core.route

import com.example.intermodular.core.network.RetrofitHelper
import com.example.intermodular.core.route.client.RouteClient
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

            val locations: Array<GpsMeasure> = routeResponse.measures.map { measure ->
                val waypoint: Waypoint? = if (measure.waypoint == null) null else Waypoint(measure.waypoint.name, measure.waypoint.description, measure.waypoint.imagePath)
                return@map GpsMeasure(measure.latitude, measure.longitude, waypoint)
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


            return@withContext Route(
                uid = routeResponse.uid,
                name = routeResponse.name,
                description = routeResponse.description,
                imageID = routeResponse.imagePath,
                lengthInKm = routeResponse.length,
                locations = locations,
                types = types,
                difficulty = difficulty,
                creator = routeResponse.creator,
                creationDatetime = LocalDateTime.ofEpochSecond(routeResponse.creationDatetime / 1000, 0, ZoneOffset.UTC)
            )
        }
    }
}