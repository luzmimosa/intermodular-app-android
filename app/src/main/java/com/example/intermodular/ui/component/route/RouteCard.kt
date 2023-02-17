package com.example.intermodular.ui.component.route

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.intermodular.core.route.model.Route
import com.example.intermodular.preview.RoutePreviewProvider
import com.example.intermodular.R
import com.example.intermodular.core.route.model.RouteDifficulty
import com.example.intermodular.core.route.model.RouteType
import com.example.intermodular.ui.component.global.SmallProfilePicture
import com.example.intermodular.ui.theme.RouteDifficultyDarkColors
import com.example.intermodular.ui.theme.RouteDifficultyLightColors

@Preview
@Composable
fun RouteCard(@PreviewParameter(RoutePreviewProvider::class) route: Route) {
    Card(modifier = Modifier
        .padding(5.dp, 5.dp, 5.dp, 5.dp)
        .height(220.dp)
        .fillMaxWidth()
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            val difficultyColorScheme = if (isSystemInDarkTheme()) RouteDifficultyDarkColors else RouteDifficultyLightColors
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(8.dp)
                    .background(
                        when (route.difficulty) {
                            RouteDifficulty.TRIVIAL -> difficultyColorScheme.trivial
                            RouteDifficulty.EASY -> difficultyColorScheme.easy
                            RouteDifficulty.MEDIUM -> difficultyColorScheme.medium
                            RouteDifficulty.HARD -> difficultyColorScheme.hard
                            RouteDifficulty.EXPERT -> difficultyColorScheme.expert
                            else -> difficultyColorScheme.trivial
                        }
                    )
            ) {
                
            }

            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.5f)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.app_background),
                        contentDescription = stringResource(id = R.string.route_mainpicture_description).format(route.name),
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.7f)
                        .padding(5.dp)
                ) {

                    // Titulo y descripción
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(0.8f)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.4f)
                        ) {
                            CardTitleText(title = route.name)
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.8f)
                        ) {
                            CardSubtitleText(subtitle = route.description)
                        }
                    }
                    
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        
                        RouteTypesIcons(route = route)
                        
                        Row(modifier = Modifier.fillMaxSize()) {
                            Box(
                                modifier = Modifier
                                    .padding(0.dp)
                                    .fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                LikeCounterText(likes = route.likes)
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp, 0.dp, 0.dp, 5.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                    ) {
                        CardUser(username = route.creator)
                    }
                }
            }
        }
    }
}

@Composable
fun CardTitleText(title: String) {
    Text(
        text = title,
        modifier = Modifier.padding(5.dp),
        fontSize = 20.sp,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1
    )
}

@Composable
fun CardSubtitleText(subtitle: String) {
    Text(
        text = subtitle,
        modifier = Modifier.padding(5.dp),
        fontSize = 12.sp,
        overflow = TextOverflow.Ellipsis,
        maxLines = 2
    )
}

@Composable
fun CardUser(username: String) {

    Row(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .fillMaxHeight()
            .width(30.dp)
        ) {
            Box(
                modifier = Modifier
                    .padding(0.dp)
                    .fillMaxSize(),
                contentAlignment = Alignment.CenterEnd
            ) {
                SmallProfilePicture(username = username)
            }
        }

        Column(modifier = Modifier
            .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .padding(2.dp, 0.dp, 0.dp, 4.dp)
                    .fillMaxSize(),
                contentAlignment = Alignment.CenterStart
            ) {
                CardUsernameText(username = username)
            }
        }
    }
}

@Composable
fun CardUsernameText(username: String) {
    Text(
        text = "@$username",
        modifier = Modifier
            .padding(0.dp),
        fontSize = 12.sp,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        textAlign = TextAlign.Center
    )
}

@Composable
fun LikeCounterText(likes: Int) {

    Row(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(0.8f)
        ) {
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxSize(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = "$likes",
                    modifier = Modifier
                        .padding(0.dp),
                    fontSize = 12.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    textAlign = TextAlign.Center
                )
            }
        }

        Column(modifier = Modifier
            .fillMaxSize()
        ) {
            Icon(
                imageVector = Icons.Filled.FavoriteBorder,
                contentDescription = stringResource(id = R.string.route_likes_description).format(likes.toString()),
                modifier = Modifier
                    .padding(0.dp)
                    .fillMaxSize()
            )
        }
    }
}

@Composable
fun RouteTypesIcons(route: Route) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.3f)
            .padding(2.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.25f)
        ) {
            if (route.types.size >= 4) {
                Icon(
                    imageVector = routeTypeIcon(route.types[3]),
                    contentDescription = stringResource(id = routeTypeCaption(route.types[0])),
                    modifier = Modifier
                        .padding(0.dp)
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(1 / 3f)
        ) {
            if (route.types.size >= 3) {
                Icon(
                    imageVector = routeTypeIcon(route.types[2]),
                    contentDescription = stringResource(id = routeTypeCaption(route.types[0])),
                    modifier = Modifier
                        .padding(0.dp)
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.5f)
        ) {
            if (route.types.size >= 2) {
                Icon(
                    imageVector = routeTypeIcon(route.types[1]),
                    contentDescription = stringResource(id = routeTypeCaption(route.types[0])),
                    modifier = Modifier
                        .padding(0.dp)
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        ) {
            if (route.types.size >= 1) {
                Icon(
                    imageVector = routeTypeIcon(route.types[0]),
                    contentDescription = stringResource(id = routeTypeCaption(route.types[0])),
                    modifier = Modifier
                        .padding(0.dp)
                )
            }
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.55f)
            .padding(2.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.25f)
        ) {
            if (route.types.size >= 8) {
                Icon(
                    imageVector = routeTypeIcon(route.types[7]),
                    contentDescription = stringResource(id = routeTypeCaption(route.types[0])),
                    modifier = Modifier
                        .padding(0.dp)
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(1 / 3f)
        ) {
            if (route.types.size >= 7) {
                Icon(
                    imageVector = routeTypeIcon(route.types[6]),
                    contentDescription = stringResource(id = routeTypeCaption(route.types[0])),
                    modifier = Modifier
                        .padding(0.dp)
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.5f)
        ) {
            if (route.types.size >= 6) {
                Icon(
                    imageVector = routeTypeIcon(route.types[5]),
                    contentDescription = stringResource(id = routeTypeCaption(route.types[0])),
                    modifier = Modifier
                        .padding(0.dp)
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        ) {
            if (route.types.size >= 5) {
                Icon(
                    imageVector = routeTypeIcon(route.types[4]),
                    contentDescription = stringResource(id = routeTypeCaption(route.types[0])),
                    modifier = Modifier
                        .padding(0.dp)
                )
            }
        }
    }
}

fun routeTypeIcon(type: RouteType): ImageVector {
    return when (type) {
        RouteType.BYCICLE -> Icons.Filled.DirectionsBike
        RouteType.RUNNING -> Icons.Filled.DirectionsRun
        RouteType.PHOTOGRAPHY -> Icons.Filled.PhotoCamera
        RouteType.TREKKING -> Icons.Filled.Hiking
        RouteType.WALK -> Icons.Filled.DirectionsWalk
        else -> Icons.Filled.Directions
    }
}

fun routeTypeCaption(type: RouteType): Int {
    return when (type) {
        RouteType.BYCICLE -> R.string.route_type_bike_description
        RouteType.RUNNING -> R.string.route_type_running_description
        RouteType.PHOTOGRAPHY -> R.string.route_type_photography_description
        RouteType.TREKKING -> R.string.route_type_trekking_description
        RouteType.WALK -> R.string.route_type_walk_description
        else -> R.string.route_type_other_description
    }
}