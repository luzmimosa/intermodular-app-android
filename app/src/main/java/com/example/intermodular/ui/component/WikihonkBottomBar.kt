package com.example.intermodular.ui.component

import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.intermodular.R
import com.example.intermodular.model.Routes

@Composable
fun WikihonkBottomBar(navigationController : NavHostController){
    BottomNavigation() {
        IconButton(onClick= { navigationController.navigate(Routes.MapScreen.route)}){
            Icon(
                imageVector = Icons.Default.MyLocation,
                contentDescription= stringResource(id = R.string.navigation_bottombar_map),
                tint= Color.White,
                modifier= Modifier.size(40.dp)
            )
        }

        IconButton(onClick= { navigationController.navigate(Routes.HomeScreen.route)}){
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription= stringResource(id = R.string.navigation_bottombar_home),
                tint= Color.White,
                modifier= Modifier.size(40.dp)
            )
        }

        IconButton(onClick= { navigationController.navigate(Routes.FavScreen.route)}){
            Icon(
                imageVector= Icons.Default.Favorite,
                contentDescription= stringResource(id = R.string.navigation_bottombar_favourites),
                tint= Color.White,
                modifier= Modifier.size(40.dp)
            )
        }
    }
}
