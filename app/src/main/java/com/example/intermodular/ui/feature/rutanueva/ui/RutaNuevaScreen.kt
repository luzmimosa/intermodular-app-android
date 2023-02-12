package com.example.intermodular.ui.feature.rutanueva.ui

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.intermodular.ui.component.global.WikihonkBottomBar
import com.example.intermodular.ui.component.global.WikihonkTopBar

@Composable
fun RutaNueva(rutanuevaviewmodel: RutaNuevaViewModel, navigationController: NavHostController){
    Scaffold(
        topBar= {
            WikihonkTopBar(navigationController)
        },

        bottomBar= {
            WikihonkBottomBar(navigationController)
        }
    ) {

    }
}