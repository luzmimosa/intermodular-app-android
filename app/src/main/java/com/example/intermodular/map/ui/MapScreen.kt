package com.example.intermodular.map.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.intermodular.home.ui.BottomBar
import com.example.intermodular.home.ui.TopBar
import com.example.intermodular.model.Routes
import com.example.intermodular.ui.theme.verde1
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapScreen(mapViewModel: MapViewModel, navigationController: NavHostController){
    val localizacion = LatLng(38.55359897196608, -0.12057169825429333)
    val cameraPositionState= rememberCameraPositionState{
        position = CameraPosition.fromLatLngZoom(localizacion, 13f)
    }

    Scaffold(
        topBar= {
            TopBar(navigationController)
        },

        bottomBar= {
            BottomBar(navigationController)
        }
    ) {
        Box(modifier= Modifier
            .fillMaxSize()
            .padding(8.dp)
        ){
            GoogleMap(
                modifier= Modifier.fillMaxSize(),
                cameraPositionState= CameraPositionState(),

                ){
                Polyline(points = listOf(
                    LatLng(38.55359897196608, -0.12057169825429333),
                    LatLng(39.55359897196608, -0.12057169825429333),
                    LatLng(40.55359897196608, -0.12057169825429333),
                    LatLng(41.55359897196608, -0.12057169825429333)
                ),
                    color= Color.Red
                )
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
                text= "WIKIHONK",
                color= Color.White,
                fontSize = 30.sp,
                textAlign = TextAlign.Center
            )
        },
        backgroundColor = verde1,
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
    BottomNavigation(
        backgroundColor = verde1
    ) {
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