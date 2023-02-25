package com.example.intermodular.ui.component.global

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.intermodular.R
import com.example.intermodular.core.user.ServerUserManager

@Composable
fun ProfilePicture(username: String?) {
    Image(
        bitmap = profilePictureOf(username = username),
        contentDescription = stringResource(id = R.string.global_no_profile_picture),
        modifier = Modifier
            .clip(CircleShape)
            .border(0.dp, Color.Transparent, CircleShape)
            .size(100.dp),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun SmallProfilePicture(username: String?) {
    Image(
        bitmap = profilePictureOf(username = username),
        contentDescription = stringResource(id = R.string.global_no_profile_picture),
        modifier = Modifier
            .clip(CircleShape)
            .border(0.dp, Color.Transparent, CircleShape)
            .size(25.dp),
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun profilePictureOf(username: String?): ImageBitmap {

    if (username == null) return ImageBitmap.imageResource(id = R.drawable.app_background)

    val user = ServerUserManager.userFromCache(username = username) ?: return ImageBitmap.imageResource(id = R.drawable.default_proofile_pic)
    return user.profilePicture
}