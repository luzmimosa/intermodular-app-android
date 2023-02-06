package com.example.intermodular.home.ui

import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
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
    ){ }
}

@Composable
fun TopBar(navigationController : NavHostController){
    TopAppBar(
        navigationIcon= {
            IconButton(onClick= {/* */}){
                Icon(
                    imageVector= Icons.Filled.Menu,
                    contentDescription= "...",
                    tint= Color.White
                )
            }
        },
        title= {
            Text(
                text= "WIKIHONK",
                color= Color.White
            )
        },
        backgroundColor = MaterialTheme.colors.primary,
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
        backgroundColor = MaterialTheme.colors.primary
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



