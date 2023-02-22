package com.example.intermodular.core.route

import android.util.Log
import com.example.intermodular.core.network.RetrofitHelper
import com.example.intermodular.core.route.client.NearRoutesClient
import com.example.intermodular.core.route.client.RandomRoutesClient
import com.example.intermodular.core.route.client.RouteClient
import com.example.intermodular.core.route.client.RouteUploadClient
import com.example.intermodular.core.route.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.ZoneOffset

object ServerRouteManager {

    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getRouteByID(routeID: String): Route? {
        return withContext(Dispatchers.IO) {
            try {
                val response = retrofit.create(RouteClient::class.java).getRoute(routeID)

                if (!response.isSuccessful) return@withContext null

                val routeResponse = response.body() ?: return@withContext null

                val locations: Array<ServerGpsMeasure> = routeResponse.measures.map { measure ->
                    val waypoint: ServerWaypoint? =
                        if (measure.waypoint == null) null else ServerWaypoint(
                            measure.waypoint.name,
                            measure.waypoint.description,
                            measure.waypoint.imagePath
                        )
                    return@map ServerGpsMeasure(measure.latitude, measure.longitude, waypoint)
                }.toTypedArray()
                val types: Array<RouteType> =
                    if (routeResponse.types == null) arrayOf() else routeResponse.types.map { type ->
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

                val comments: Array<ServerComment> = routeResponse.comments.map { comment ->
                    return@map ServerComment(comment.username, comment.comment, LocalDateTime.ofEpochSecond(
                        comment.datetime / 1000,
                        0,
                        ZoneOffset.UTC
                    ))
                }.toTypedArray()

                try {
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
                        creationDatetime = LocalDateTime.ofEpochSecond(
                            routeResponse.creationDatetime / 1000,
                            0,
                            ZoneOffset.UTC
                        ),
                        likes = routeResponse.likes,
                        comments = comments
                    ).asRoute()
                } catch (exception: Exception) {
                    Log.e("ServerRouteManager", "Error while resolving route by ID", exception)
                    return@withContext null
                }
            } catch (exception: Exception) {
                Log.e("ServerRouteManager", "Error while getting route by ID", exception)
                return@withContext null
            }
        }
    }

    suspend fun provideRoutesByLocation(
        latitude: Double,
        longitude: Double,
        radius: Int = 30,
        provide: (route: Route) -> Unit
    ) {

        withContext(Dispatchers.IO) {
            this.launch {
                try {
                    val routeIdsResponse = retrofit.create(NearRoutesClient::class.java)
                        .getNearRoutes(latitude, longitude, radius)
                    if (!routeIdsResponse.isSuccessful) return@launch
                    val routeIds = routeIdsResponse.body() ?: return@launch

                    for (routeID in routeIds) {
                        this.launch {
                            try {
                                val route = getRouteByID(routeID)
                                if (route != null) {
                                    provide(route)
                                }
                            } catch (exception: Exception) {
                                Log.e("ServerRouteManager", "Error while resolving route by location", exception)
                            }
                        }
                    }
                } catch (exception: Exception) {
                    Log.e("ServerRouteManager", "Error while getting routes by location", exception)
                    return@launch
                }
            }
        }
    }

    suspend fun provideRandomRoutes(
        provide: (route: Route) -> Unit
    ){

        withContext(Dispatchers.IO) {
            this.launch {
                try {
                    val response = retrofit.create(RandomRoutesClient::class.java).getRandomRoutes()
                    if (!response.isSuccessful) return@launch

                    val routeIdsResponse = response.body() ?: return@launch

                    for (routeID in routeIdsResponse) {
                        this.launch {
                            try {
                                val route = getRouteByID(routeID)
                                if (route != null) {
                                    provide(route)
                                }
                            } catch (exception: Exception) {
                                Log.e("ServerRouteManager", "Error while resolving random route", exception)
                            }
                        }
                    }
                } catch (exception: Exception) {
                    Log.e("ServerRouteManager", "Error while getting random routes", exception)
                    return@launch
                }
            }
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