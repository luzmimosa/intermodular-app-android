package com.example.intermodular.ui.feature.favoritos.ui

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.intermodular.ui.component.WikihonkBottomBar
import com.example.intermodular.ui.component.WikihonkTopBar

@Composable
fun Fav(favViewModel: FavViewModel, navigationController: NavHostController){
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