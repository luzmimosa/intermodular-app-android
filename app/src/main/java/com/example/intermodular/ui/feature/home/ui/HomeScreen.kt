package com.example.intermodular.ui.feature.home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.intermodular.R
import com.example.intermodular.model.Routes

@Composable
fun Home(homeviewmodel : HomeViewModel, navigationController: NavHostController){

    Scaffold(
        topBar= {
            TopBar(navigationController)
        },

        bottomBar= {
            BottomBar(navigationController)
        }
    ){

        Column(modifier= Modifier.background(color= Color.Gray)) {
            Row(){
                Card(modifier = Modifier
                    .padding(5.dp, 5.dp, 5.dp, 5.dp)
                    .height(200.dp)
                    .fillMaxSize()
                    .clickable { navigationController.navigate(Routes.InfoRuta.route) }) {
                    Column() {
                        Row(){
                            Image(
                                painter= painterResource(id= R.drawable.black),
                                contentDescription = stringResource(id = R.string.home_alt_route_image),
                                modifier= Modifier.size(120.dp)
                            )
                        }

                        Row(modifier= Modifier.padding(5.dp)){
                            Image(
                                painter= painterResource(id= R.drawable.black),
                                contentDescription = stringResource(id = R.string.home_alt_user_image),
                                modifier= Modifier
                                    .clip(CircleShape)
                                    .border(0.dp, Color.Transparent, CircleShape)
                                    .size(60.dp)
                            )

                            Box(){
                                Column() {
                                    Row(){
                                        Text(
                                            text= "Nombre de la ruta",
                                            modifier= Modifier.padding(10.dp, 5.dp)
                                        )
                                    }

                                    Row(){
                                        Text(
                                            text= "Usuario",
                                            modifier= Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)
                                        )
                                    }
                                }
                            }

                            Box(modifier = Modifier.border(100.dp, Color.Transparent)){
                                Icon(
                                    imageVector = Icons.Default.Favorite,
                                    contentDescription = stringResource(id = R.string.home_alt_like_count),
                                    modifier= Modifier
                                        .size(35.dp)
                                        .padding(5.dp),
                                    tint= Color.Red
                                )

                                Text(
                                    text="0",
                                    modifier= Modifier.padding(30.dp, 10.dp, 0.dp, 0.dp)
                                )
                            }

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TopBar(navigationController : NavHostController){
    TopAppBar(
        navigationIcon= {
            IconButton(onClick= { navigationController.navigate(Routes.RutaNuevaScreen.route)}){
                Icon(
                    imageVector= Icons.Filled.Add,
                    contentDescription= "...",
                    tint= Color.White
                )
            }
        },
        title= {
            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(0.dp, 5.dp, 0.dp, 0.dp),
                text= stringResource(id = R.string.global_app_name),
                color= Color.White,
                fontSize = 30.sp,
                textAlign = TextAlign.Center
            )
        },
        actions= {
            IconButton(onClick= { navigationController.navigate(Routes.UserInfoScreen.route)}){
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription= "...",
                    tint= Color.White
                )
            }
        }
    )
}

@Composable
fun BottomBar(navigationController : NavHostController){
    BottomNavigation() {
        IconButton(onClick= { navigationController.navigate(Routes.MapScreen.route)}){
            Icon(
                imageVector = Icons.Default.MyLocation,
                contentDescription= "...",
                tint= Color.White,
                modifier= Modifier.size(40.dp)
            )
        }

        IconButton(onClick= { navigationController.navigate(Routes.HomeScreen.route)}){
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription= "...",
                tint= Color.White,
                modifier= Modifier.size(40.dp)
            )
        }

        IconButton(onClick= { navigationController.navigate(Routes.FavScreen.route)}){
            Icon(
                imageVector= Icons.Default.Favorite,
                contentDescription= "...",
                tint= Color.White,
                modifier= Modifier.size(40.dp)
            )
        }
    }
}


