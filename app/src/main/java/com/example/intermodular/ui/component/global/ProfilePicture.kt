package com.example.intermodular.ui.component.global

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.intermodular.R

@Composable
fun ProfilePicture(username: String?) {
    Image(
        painter = profilePictureOf(username = username),
        contentDescription = stringResource(id = R.string.global_no_profile_picture),
        modifier = Modifier
            .clip(CircleShape)
            .border(0.dp, Color.Transparent, CircleShape)
            .size(100.dp)
    )
}

@Composable
fun SmallProfilePicture(username: String?) {
    Image(
        painter = profilePictureOf(username = username),
        contentDescription = stringResource(id = R.string.global_no_profile_picture),
        modifier = Modifier
            .clip(CircleShape)
            .border(0.dp, Color.Transparent, CircleShape)
            .size(25.dp)
    )
}

@Composable
private fun profilePictureOf(username: String?): Painter {
    if (username == null) return painterResource(id = R.drawable.default_proofile_pic)

    return painterResource(id = R.drawable.default_proofile_pic)
}