package com.example.intermodular.ui.component.global

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun WikihonkBaseScreen(
    navigationController: NavHostController,
    showBottomBar: Boolean = false,
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            WikihonkTopBar(navigationController)
        },

        bottomBar = {
            if (showBottomBar) {
                WikihonkBottomBar(navigationController)
            }
        }

    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = if (showBottomBar) bottomAppBarHeight else 0.dp)
        ) {
            content()
        }
    }
}