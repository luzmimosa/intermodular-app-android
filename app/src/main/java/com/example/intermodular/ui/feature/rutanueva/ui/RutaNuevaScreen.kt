package com.example.intermodular.ui.feature.rutanueva.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.intermodular.R
import com.example.intermodular.model.Routes

@Composable
fun RutaNueva(rutanuevaviewmodel: RutaNuevaViewModel, navigationController: NavHostController){
    Scaffold(
        topBar= {
            com.example.intermodular.ui.feature.home.ui.TopBar(navigationController)
        },

        bottomBar= {
            com.example.intermodular.ui.feature.home.ui.BottomBar(navigationController)
        }
    ) {

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
                modifier = Modifier.fillMaxSize().padding(0.dp,5.dp,0.dp,0.dp),
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