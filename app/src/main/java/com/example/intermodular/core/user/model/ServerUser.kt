package com.example.intermodular.core.user.model

import com.example.intermodular.core.image.ServerImageManager
import com.google.gson.annotations.SerializedName

data class ServerUser(
    @SerializedName("username")         val username: String,
    @SerializedName("displayName")      val displayName: String,
    @SerializedName("profilePicture")   val profilePicture: String,
    @SerializedName("biography")        val bio: String,
    @SerializedName("featuredRoutes")   val featuredRoutes: Array<String>,
    @SerializedName("toDoRoutes")       val toDoRoutes: Array<String>,
) {
    suspend fun asUser(): User {
        return User(
            username,
            displayName,
            ServerImageManager.getImage(profilePicture)!!,
            bio,
            featuredRoutes.toMutableList(),
            toDoRoutes.toMutableList(),
        )
    }
}
