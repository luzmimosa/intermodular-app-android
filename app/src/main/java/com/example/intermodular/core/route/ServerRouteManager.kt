package com.example.intermodular.core.route

import android.util.Log
import com.example.intermodular.core.network.RetrofitHelper
import com.example.intermodular.core.route.client.*
import com.example.intermodular.core.route.model.*
import com.example.intermodular.core.user.ServerUserManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

object ServerRouteManager {

    private val retrofit = RetrofitHelper.getRetrofit()

    private val routeCache: MutableMap<String, Route> = mutableMapOf()

    suspend fun getRouteByID(routeID: String, updateCache: Boolean = false): Route? {

        if (routeCache.containsKey(routeID) && !updateCache) {
            return routeCache[routeID]
        }

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
                    return@map ServerComment(
                        comment.username,
                        comment.comment,
                        LocalDateTime.ofInstant(Instant.ofEpochMilli(comment.date), ZoneOffset.UTC),
                    )
                }.toTypedArray()

                try {
                    val route = ServerRoute(
                        uid = routeResponse.uid,
                        name = routeResponse.name,
                        description = routeResponse.description,
                        imageID = routeResponse.imagePath,
                        lengthInKm = routeResponse.length,
                        locations = locations,
                        types = types,
                        difficulty = difficulty,
                        creator = routeResponse.creator,
                        creationDatetime = LocalDateTime.ofInstant(Instant.ofEpochMilli(routeResponse.creationDatetime), ZoneOffset.UTC),
                        likes = routeResponse.likes,
                        comments = comments
                    ).asRoute()

                    routeCache[routeID] = route
                    ServerUserManager.getUserByUsername(route.creator)

                    return@withContext route
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

    suspend fun getCreatedRoutesList(username: String): Array<String> {
        return withContext(Dispatchers.IO) {
            try {
                val response = retrofit.create(RouteListClient::class.java).getUserRoutes(username)
                if (!response.isSuccessful) return@withContext emptyArray()
                return@withContext response.body() ?: return@withContext emptyArray()
            } catch (exception: Exception) {
                return@withContext emptyArray()
            }
        }
    }

    suspend fun getLocatedRoutesList(latitude: Double, longitude: Double): Array<String> {
        return withContext(Dispatchers.IO) {
            try {
                val response = retrofit.create(RouteListClient::class.java).getNearRoutes(latitude, longitude)
                if (!response.isSuccessful) return@withContext emptyArray()
                return@withContext response.body() ?: return@withContext emptyArray()
            } catch (exception: Exception) {
                return@withContext emptyArray()
            }
        }
    }

    suspend fun getRandomRoutesList(): Array<String> {
        return withContext(Dispatchers.IO) {
            try {
                val response = retrofit.create(RouteListClient::class.java).getRandomRoutes()
                if (!response.isSuccessful) return@withContext emptyArray()
                return@withContext response.body() ?: return@withContext emptyArray()
            } catch (exception: Exception) {
                return@withContext emptyArray()
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

    suspend fun commentRoute(routeID: String, comment: String): Boolean {
        try {
            return withContext(Dispatchers.IO) {
                val response = retrofit.create(RouteApiClient::class.java).commentRoute(routeID, UploadableComment(comment))
                return@withContext response.isSuccessful
            }
        } catch (exception: Exception) {
            return false
        }
    }

    suspend fun likeRoute(routeID: String): Boolean {
        try {
            return withContext(Dispatchers.IO) {
                val response = retrofit.create(RouteApiClient::class.java).likeRoute(routeID)
                return@withContext response.isSuccessful
            }
        } catch (exception: Exception) {
            return false
        }
    }

    suspend fun unlikeRoute(routeID: String): Boolean {
        try {
            return withContext(Dispatchers.IO) {
                val response = retrofit.create(RouteApiClient::class.java).unlikeRoute(routeID)
                return@withContext response.isSuccessful
            }
        } catch (exception: Exception) {
            return false
        }
    }

    suspend fun addToToDoList(routeID: String): Boolean {
        try {
            return withContext(Dispatchers.IO) {
                val response = retrofit.create(RouteApiClient::class.java).add(routeID)
                return@withContext response.isSuccessful
            }
        } catch (exception: Exception) {
            return false
        }
    }

    suspend fun removeFromToDoList(routeID: String): Boolean {
        try {
            return withContext(Dispatchers.IO) {
                val response = retrofit.create(RouteApiClient::class.java).remove(routeID)
                return@withContext response.isSuccessful
            }
        } catch (exception: Exception) {
            return false
        }
    }

    fun getRouteCache(): List<Route> {
        return routeCache.values.toList()
    }
}