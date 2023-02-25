package com.example.intermodular.ui.component.global

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.intermodular.R
import com.example.intermodular.model.Routes

@Preview
@Composable
fun WikihonkTopBar(navigationController : NavHostController = rememberNavController()){
    TopAppBar(
        navigationIcon = {
            IconButton(onClick= { navigationController.navigate(Routes.RutaNuevaScreen.route)}){
                Icon(
                    imageVector= Icons.Filled.Add,
                    contentDescription= stringResource(id = R.string.navigation_topbar_newroute),
                    tint= Color.White
                )
            }
        },

        title = {
            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(0.dp, 5.dp, 0.dp, 0.dp),
                text= stringResource(id = R.string.app_name),
                color= Color.White,
                fontSize = 30.sp,
                textAlign = TextAlign.Center
            )
        },
        actions = {
            Spacer(Modifier.width(45.dp))
        }
    )
}