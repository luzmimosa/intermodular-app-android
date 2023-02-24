package com.example.intermodular.ui.component.global

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.intermodular.R
import com.example.intermodular.model.Routes

val bottomAppBarHeight = 60.dp

@Composable
fun WikihonkBottomBar(navigationController : NavHostController) {

    BottomNavigation(
        modifier = Modifier.height(bottomAppBarHeight)
    ) {
        IconButton(
            onClick= {
                navigationController.navigate(Routes.MapScreen.route(null))
            }
        ) {
            Icon(
                imageVector = Icons.Default.MyLocation,
                contentDescription= stringResource(id = R.string.navigation_bottombar_map),
                tint= Color.White,
                modifier= Modifier.size(40.dp)
            )
        }

        IconButton(onClick= {
            if (navigationController.currentDestination?.route != Routes.HomeScreen.route) {
                navigationController.navigate(Routes.HomeScreen.route)
            }
        }){
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription= stringResource(id = R.string.navigation_bottombar_home),
                tint= Color.White,
                modifier= Modifier.size(40.dp)
            )
        }

        IconButton(onClick= { navigationController.navigate(Routes.UserInfoScreen.route)}){
            Icon(
                imageVector= Icons.Default.Person,
                contentDescription= stringResource(id = R.string.navigation_bottombar_profile),
                tint= Color.White,
                modifier= Modifier.size(40.dp)
            )
        }
    }
}
