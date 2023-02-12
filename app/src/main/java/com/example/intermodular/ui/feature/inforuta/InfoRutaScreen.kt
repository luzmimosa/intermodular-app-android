package com.example.intermodular.ui.feature.inforuta

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.intermodular.ui.component.global.WikihonkBottomBar
import com.example.intermodular.ui.component.global.WikihonkTopBar

@Composable
fun InfoRuta(infoRutaViewModel: InfoRutaViewModel, navigationController: NavHostController){
    Scaffold(
        topBar= {
            WikihonkTopBar(navigationController = navigationController)
        },

        bottomBar= {
            WikihonkBottomBar(navigationController)
        }
    ) {

    }
}