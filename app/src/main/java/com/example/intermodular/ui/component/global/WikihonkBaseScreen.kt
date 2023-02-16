package com.example.intermodular.ui.component.global

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun WikihonkBaseScreen(
    navigationController: NavHostController,
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            WikihonkTopBar(navigationController)
        },

        bottomBar = {
            WikihonkBottomBar(navigationController)
        }

    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = bottomAppBarHeight)
        ) {
            content()
        }
    }
}