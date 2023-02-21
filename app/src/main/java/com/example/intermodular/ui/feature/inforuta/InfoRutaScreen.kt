package com.example.intermodular.ui.feature.inforuta

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.intermodular.R
import com.example.intermodular.core.route.model.Route
import com.example.intermodular.core.route.model.RouteDifficulty
import com.example.intermodular.core.route.model.RouteType
import com.example.intermodular.model.Routes
import com.example.intermodular.ui.component.global.*
import com.example.intermodular.ui.component.route.routeTypeIcon
import kotlinx.coroutines.delay
import java.time.LocalDateTime

@Composable
fun InfoRuta(
    infoRutaViewModel: InfoRutaViewModel,
    navigationController: NavHostController
){

    val route: Route by infoRutaViewModel.route.observeAsState(initial = Route(
        uid = "0",
        name = "...",
        description = "...",
        image = ImageBitmap(1, 1),
        lengthInKm = 0.0,
        locations = arrayOf(),
        types = arrayOf(RouteType.BYCICLE, RouteType.WALK, RouteType.RUNNING),
        difficulty = RouteDifficulty.MEDIUM,
        creator = "...",
        creationDatetime = LocalDateTime.now(),
        likes = 0
    ))

    val isResolved by infoRutaViewModel.isRouteResolved.observeAsState(initial = false)

    LaunchedEffect(true) {
        infoRutaViewModel.fetchRoute()
        while (!isResolved) {
            infoRutaViewModel.resolveRoute()
            delay(1000)
        }
    }

    WikihonkBaseScreen(
        navigationController = navigationController
    ) {
        Column(modifier= Modifier
            .fillMaxSize()
            .background(color = Color.Gray)
        ){

            RouteHeader(route, navigationController)

            Row(modifier= Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .background(color = Color.White)){
                //redondear bordes
                Text(
                    text = "COMENTARIOS",
                    fontSize = 27.sp,
                    modifier= Modifier.padding(90.dp, 10.dp),
                    fontWeight = FontWeight.Bold
                )
            }

            Row(modifier= Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .background(color = Color.White)){
                //redondear bordes
                //poner imagen mas grande
                //añadir lazy column para la tarjeta de comentarios

                Box {
                    SmallProfilePicture(username = "wpf")
                }

                Box(modifier= Modifier.padding(10.dp)) {
                    Text(
                        text = "Nombre de usuario",
                        fontSize = 20.sp,
                        modifier = Modifier.padding(10.dp, 5.dp, 0.dp, 0.dp)
                    )

                    Text(
                        text = "Cuerpo del comentario",
                        fontSize = 15.sp,
                        modifier = Modifier.padding(10.dp, 35.dp, 0.dp, 0.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun RouteHeader(
    route: Route,
    navigationController: NavHostController = rememberNavController(),
    isFavorite: Boolean = false,
    isToDo: Boolean = false,
    onFavPress: () -> Unit = {},
    onToDoPress: () -> Unit = {}
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.surface)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            ) {
                Image(
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

                    Row(
                        modifier = Modifier
                            .fillMaxHeight()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight(0.9f),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            ClickableText(
                                text = "Ver ruta",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                underlined = true
                            ) {
                                navigationController.navigate(Routes.MapScreen.route)
                            }
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