package com.example.intermodular.core.user

import androidx.compose.ui.graphics.ImageBitmap
import com.example.intermodular.core.image.ServerImageManager
import com.example.intermodular.core.network.RetrofitHelper
import com.example.intermodular.core.route.ServerRouteManager
import com.example.intermodular.core.user.client.UserClient
import com.example.intermodular.core.user.model.ServerModifiedUser
import com.example.intermodular.core.user.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object ServerUserManager {

    private val retrofit = RetrofitHelper.getRetrofit()

    private val userCache: MutableMap<String, User> = mutableMapOf()

    private var _selfUser: User? = null

    private var selfUserImageId = ""

    suspend fun getUserByUsername(username: String, forceSync: Boolean = false): User? {
        if (userCache.containsKey(username) && !forceSync) return userCache[username]

        return try {
            withContext(Dispatchers.IO) {

                val response = retrofit.create(UserClient::class.java).getUserByUsername(username)

                if (!response.isSuccessful) return@withContext null

                val userResponse = response.body() ?: return@withContext null
                val user = userResponse.asUser()

                userCache[username] = user

                return@withContext user
            }
        } catch (exception: Exception) {
            return null
        }
    }

    fun userFromCache(username: String): User? {
        return userCache[username]
    }

    fun getCachedUsers(): List<User> {
        return userCache.values.toList()
    }

    suspend fun getSelfUser(forceSync: Boolean = false): User? {
        if (_selfUser != null && !forceSync) return _selfUser

        _selfUser = syncSelfUser()
        return _selfUser
    }

    fun getSelfUserOrNull(): User? {
        return _selfUser
    }

    fun isFavouriteRoute(routeID: String): Boolean {
        val user = getSelfUserOrNull() ?: return false

        user.featuredRoutes.forEach { route ->
            if (route == routeID) return true
        }

        return false
    }

    fun isToDoRoute(routeID: String): Boolean {
        val user = getSelfUserOrNull() ?: return false

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

                selfUserImageId = userResponse.profilePicture

                return@withContext userResponse.asUser()
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
            return null
        }
    }

    suspend fun setProfilePicture(image: ImageBitmap): Boolean {
        val imageID = ServerImageManager.uploadImage(image).takeIf { it != "I_DONT_WANNA_BE_A_LINK" } ?: return false
        val selfUser = getSelfUser() ?: return false

        return modifyUser(
            ServerModifiedUser(
                selfUser.displayName,
                selfUser.bio,
                imageID
            )
        )
    }

    suspend fun setDisplayName(displayName: String): Boolean {
        val selfUser = getSelfUser() ?: return false

        return modifyUser(
            ServerModifiedUser(
                displayName,
                selfUser.bio,
                selfUserImageId
            )
        )
    }

    suspend fun setBio(bio: String): Boolean {
        val selfUser = getSelfUser() ?: return false

        return modifyUser(
            ServerModifiedUser(
                selfUser.displayName,
                bio,
                selfUserImageId
            )
        )
    }

    private suspend fun modifyUser(modifiedUser: ServerModifiedUser): Boolean {
        return try {
            withContext(Dispatchers.IO) {
                val response = retrofit.create(UserClient::class.java).modifySelfUser(modifiedUser)

                if (response.isSuccessful) {
                    _selfUser = _selfUser?.copy(
                        displayName = modifiedUser.displayName,
                        bio = modifiedUser.biography,
                        profilePicture = ServerImageManager.getImage(modifiedUser.profilePicture)!!
                    )
                } else {
                    throw Exception("Failed to modify user: ${response.code()}")
                }

                return@withContext true
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
            return false
        }
    }

}