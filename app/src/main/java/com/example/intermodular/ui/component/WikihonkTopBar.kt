package com.example.intermodular.ui.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
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
fun WikihonkTopBar(navigationController : NavHostController){
    TopAppBar(
        navigationIcon= {
            IconButton(onClick= { navigationController.navigate(Routes.RutaNuevaScreen.route)}){
                Icon(
                    imageVector= Icons.Filled.Add,
                    contentDescription= stringResource(id = R.string.navigation_topbar_newroute),
                    tint= Color.White
                )
            }
        },
        title= {
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
        actions= {
            IconButton(onClick= { navigationController.navigate(Routes.UserInfoScreen.route)}){
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription= stringResource(id = R.string.navigation_topbar_profile),
                    tint= Color.White
                )
            }
        }
    )
}