package com.example.intermodular.core.user

import com.example.intermodular.core.network.RetrofitHelper
import com.example.intermodular.core.route.ServerRouteManager
import com.example.intermodular.core.user.client.UserClient
import com.example.intermodular.core.user.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object ServerUserManager {

    private val retrofit = RetrofitHelper.getRetrofit()

    private var _selfUser: User? = null

    suspend fun getUserByUsername(username: String): User? {
        return try {
            withContext(Dispatchers.IO) {

                val response = retrofit.create(UserClient::class.java).getUserByUsername(username)

                if (!response.isSuccessful) return@withContext null

                val userResponse = response.body() ?: return@withContext null
                return@withContext userResponse.asUser()
            }
        } catch (exception: Exception) {
            return null
        }
    }

    suspend fun getSelfUser(forceSync: Boolean = false): User? {
        if (_selfUser != null && !forceSync) return _selfUser

        _selfUser = syncSelfUser()
        return _selfUser
    }

    suspend fun isFavouriteRoute(routeID: String): Boolean {
        val user = getSelfUser() ?: return false

        user.featuredRoutes.forEach { route ->
            if (route == routeID) return true
        }

        return false
    }

    suspend fun isToDoRoute(routeID: String): Boolean {
        val user = getSelfUser() ?: return false

        user.toDoRoutes.forEach { route ->
            if (route == routeID) return true
        }

        return false
    }

    suspend fun addFavouriteRoute(routeID: String) {
        val user = getSelfUser() ?: return

        user.featuredRoutes.add(routeID)
        ServerRouteManager.likeRoute(routeID)
    }

    suspend fun removeFavouriteRoute(routeID: String) {
        val user = getSelfUser() ?: return

        user.featuredRoutes.remove(routeID)
        ServerRouteManager.unlikeRoute(routeID)
    }

    suspend fun addToDoRoute(routeID: String) {
        val user = getSelfUser() ?: return

        user.toDoRoutes.add(routeID)
        ServerRouteManager.addToToDoList(routeID)
    }

    suspend fun removeToDoRoute(routeID: String) {
        val user = getSelfUser() ?: return

        user.toDoRoutes.remove(routeID)
        ServerRouteManager.removeFromToDoList(routeID)
    }

    private suspend fun syncSelfUser(): User? {
        return try {
            withContext(Dispatchers.IO) {
                val response = retrofit.create(UserClient::class.java).getSelfUser()

                if (!response.isSuccessful) throw Exception("Failed to sync self user: ${response.code()}")

                val userResponse = response.body() ?: throw Exception("Failed to sync self user: null body")
                return@withContext userResponse.asUser()
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
            return null
        }
    }

}