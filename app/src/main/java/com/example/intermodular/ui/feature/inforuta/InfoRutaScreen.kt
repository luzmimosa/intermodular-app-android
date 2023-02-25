package com.example.intermodular.ui.feature.inforuta

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.intermodular.R
import com.example.intermodular.core.route.model.Comment
import com.example.intermodular.core.route.model.Route
import com.example.intermodular.core.route.model.RouteDifficulty
import com.example.intermodular.ui.component.global.*
import com.example.intermodular.ui.component.route.routeTypeIcon
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.*
import kotlinx.coroutines.delay
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@Composable
fun InfoRuta(
    infoRutaViewModel: InfoRutaViewModel,
    navigationController: NavHostController
){

    val route: Route? by infoRutaViewModel.route.observeAsState(null)
    val uploadCommentErrorPopupVisible: Boolean by infoRutaViewModel.uploadCommentErrorPopupVisible.observeAsState(false)

    val isFavouriteRoute: Boolean by infoRutaViewModel.isFavouriteRoute.observeAsState(false)
    val isToDoRoute: Boolean by infoRutaViewModel.isToDoRoute.observeAsState(false)

    infoRutaViewModel.fetchRoute()

    LaunchedEffect(true) {
        while (true) {
            infoRutaViewModel.updateRouteFromCache()
            delay(1000)
        }
    }

    WikihonkBaseScreen(
        navigationController = navigationController
    ) {

        if (route == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        } else {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Gray)
            ) {

                item {
                    RouteHeader(
                        route = route!!,
                        isFavorite = isFavouriteRoute,
                        isToDo = isToDoRoute,
                        onFavPress = { infoRutaViewModel.handleLikePress() },
                        onToDoPress = { infoRutaViewModel.handleToDoPress() }
                    )
                }

                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                        ) {
                            Text(
                                text = route!!.description
                            )
                        }
                    }
                }

                item {
                    RouteMap(
                        route = route!!,
                        onMapPress = { infoRutaViewModel.handleMapPress() }
                    )
                }

                item {
                    RouteCommentSection(
                        route = route!!,
                        onCommentSubmit = { comment -> infoRutaViewModel.submitComment(comment) }
                    )
                }

                if (uploadCommentErrorPopupVisible) {
                    item {
                        ErrorPopup(
                            message = stringResource(id = R.string.routeinfo_comment_error),
                            buttonLabel = stringResource(id = R.string.global_popup_accept),
                        ) {
                            infoRutaViewModel.closeUploadCommentErrorPopup()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RouteHeader(
    route: Route,
    isFavorite: Boolean,
    isToDo: Boolean,
    onFavPress: () -> Unit,
    onToDoPress: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.surface)
            .padding(10.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxSize(),
                    bitmap = route.image,
                    contentDescription = stringResource(id = R.string.home_alt_route_image).format(
                        route.name
                    ),
                    contentScale = ContentScale.Crop
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(5.dp, 0.dp)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(0.dp, 0.dp, 10.dp, 0.dp)
                ) {
                    Box(modifier = Modifier.fillMaxHeight(), contentAlignment = Alignment.Center) {
                        ProfilePicture(username = route.creator)
                    }
                }
                Column {
                    Row {
                        Text(
                            text = route.name,
                            fontSize = 20.sp
                        )
                    }

                    Row {
                        Text(
                            text = "@${route.creator}",
                            fontSize = 15.sp
                        )
                    }

                    Row {
                        Text(
                            text = when (route.difficulty) {
                                RouteDifficulty.TRIVIAL -> stringResource(id = R.string.route_difficulty_trivial)
                                RouteDifficulty.EASY -> stringResource(id = R.string.route_difficulty_easy)
                                RouteDifficulty.MEDIUM -> stringResource(id = R.string.route_difficulty_medium)
                                RouteDifficulty.HARD -> stringResource(id = R.string.route_difficulty_hard)
                                RouteDifficulty.EXPERT -> stringResource(id = R.string.route_difficulty_expert)
                            },
                            fontSize = 15.sp
                        )
                    }
                    Row {
                        for (type in route.types) {
                            Column {
                                Icon(
                                    imageVector = routeTypeIcon(type),
                                    contentDescription = "Type",
                                )
                            }
                        }
                    }
                    Row {
                        Text(
                            text = route.creationDatetime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                            fontSize = 15.sp
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxHeight()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight(0.9f),
                            contentAlignment = Alignment.BottomCenter
                        ) {

                            val roundedLength = (route.lengthInKm * 100.0).roundToInt() / 100.0

                            Text(
                                text = "$roundedLength km",
                                fontSize = 15.sp
                            )
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.5f)
                    ) {
                        Box(
                            contentAlignment = Alignment.CenterEnd,
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            FavButton(isFavorite) {
                                onFavPress()
                            }
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Box(
                            contentAlignment = Alignment.CenterEnd,
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            ToDoButton(isToDo) {
                                onToDoPress()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FavButton(
    active: Boolean,
    onPress: () -> Unit
) {
    Icon(
        imageVector = if (active) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
        contentDescription = "Fav",
        modifier = Modifier
            .clickable { onPress() }
            .padding(10.dp),
        tint = if (active) Color.Red else Color.Gray
    )
}

@Composable
fun ToDoButton(
    active: Boolean,
    onPress: () -> Unit
) {
    Icon(
        imageVector = if (active) Icons.Filled.TaskAlt else Icons.Filled.AddTask,
        contentDescription = "To do",
        modifier = Modifier
            .clickable { onPress() }
            .padding(10.dp),
        tint = if (active) Color.Green else Color.Gray
    )
}

@Composable
fun RouteMap(
    route: Route,
    onMapPress: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(10.dp)
    ) {
        GoogleMap(
            onMapClick = {
                onMapPress()
            },
            cameraPositionState = CameraPositionState(
                position = CameraPosition(
                    LatLng(
                        route.locations.first().latitude,
                        route.locations.first().longitude
                    ),
                    15f,
                    0f,
                    0f
                )
            ),
            uiSettings = MapUiSettings(
                compassEnabled = false,
                indoorLevelPickerEnabled = false,
                mapToolbarEnabled = false,
                myLocationButtonEnabled = false,
                rotationGesturesEnabled = false,
                scrollGesturesEnabled = false,
                scrollGesturesEnabledDuringRotateOrZoom = false,
                tiltGesturesEnabled = false,
                zoomControlsEnabled = false,
                zoomGesturesEnabled = false
            ),
            properties = MapProperties(
                mapStyleOptions = MapStyleOptions.loadRawResourceStyle(
                    LocalContext.current,
                    if (isSystemInDarkTheme()) R.raw.dark_map_style else R.raw.light_map_style
                ),
                isMyLocationEnabled = false
            )
        ) {
            Polyline(
                points = route.locations.map {
                    LatLng(it.latitude, it.longitude)
                }
            )

            route.locations.forEach { location ->
                val waypoint = location.waypoint
                if (waypoint != null) {
                    Marker(
                         state = MarkerState(
                             position = LatLng(location.latitude, location.longitude)
                         ),
                        icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW),
                    )
                }
            }
        }
        
    }
}

@Composable
fun RouteCommentSection(
    route: Route,
    onCommentSubmit: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Column {
            CommentInput(
                onSubmit = { onCommentSubmit(it) }
            )
            for (comment in route.comments) {
                RouteComment(comment = comment)
            }
        }
    }
}

@Composable
fun CommentInput(
    onSubmit: (String) -> Unit
) {
    val inputText = remember { mutableStateOf("") }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            SmallProfilePicture(username = "username")
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                TextField(
                    value = inputText.value,
                    onValueChange = {
                        inputText.value = it
                    },
                    label = {
                        Text(text = stringResource(id = R.string.routeinfo_comment_placeholder))
                    },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Button(
                    onClick = {
                        onSubmit(inputText.value)
                        inputText.value = ""
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    enabled = inputText.value.isNotEmpty()
                ) {
                    Text(text = stringResource(id = R.string.routeinfo_comment_submit))
                }
            }
        }
    }
    
}

@Composable
fun RouteComment(
    comment: Comment
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(0.dp)
                ) {
                    SmallProfilePicture(username = comment.username)
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(3.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = comment.username,
                            fontSize = 10.sp,
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(text = comment.comment)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                    Text(
                        text = comment.datetime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                        fontSize = 10.sp,
                        color = MaterialTheme.colors.onSecondary
                    )
                }
            }
        }
    }
}