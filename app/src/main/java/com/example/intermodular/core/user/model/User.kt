package com.example.intermodular.core.user.model

import androidx.compose.ui.graphics.ImageBitmap

data class User(

    val username: String,
    val displayName: String,
    val profilePicture: ImageBitmap,
    val bio: String,
    val featuredRoutes: MutableList<String>,
    val toDoRoutes: MutableList<String>,

)
