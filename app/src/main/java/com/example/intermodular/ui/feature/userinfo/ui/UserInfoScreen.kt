package com.example.intermodular.ui.feature.userinfo.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.intermodular.R
import com.example.intermodular.ui.component.global.WikihonkBottomBar
import com.example.intermodular.ui.component.global.WikihonkTopBar

@Preview
@Composable
fun UserInfo(/*userInfoViewModel: UserInfoViewModel= UserInfoViewModel()*/ navigationController: NavHostController = rememberNavController()){
    Scaffold(
        topBar= {
            WikihonkTopBar(navigationController)
        },

        bottomBar= {
            WikihonkBottomBar(navigationController)
        }
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Gray)) {
            Row(modifier = Modifier.padding(7.dp).background(color= Color.White).fillMaxWidth()){
                    Column() {
                        Row(modifier= Modifier.padding(5.dp).fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                            Image(
                                painter = painterResource(id = R.drawable.black),
                                contentDescription = "imagen usuario",
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .border(0.dp, Color.Transparent, CircleShape)
                                    .size(100.dp)
                            )
                        }

                        Row(modifier= Modifier.padding(5.dp).fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                            Box(){
                                Text(text = "Nombre de usuario", fontSize = 20.sp)
                            }
                        }

                        Row(modifier= Modifier.padding(5.dp).fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                            Box(modifier= Modifier.padding(10.dp, 0.dp, 40.dp, 10.dp)){
                                Text(text = "0", fontSize = 15.sp , modifier= Modifier.padding(30.dp, 0.dp))
                                Text(text= "Siguiendo", fontSize = 15.sp , modifier= Modifier.padding(0.dp, 15.dp))
                            }

                            Box(modifier= Modifier.padding(0.dp, 0.dp, 10.dp, 10.dp)){
                                Text(text= "0", fontSize = 15.sp , modifier= Modifier.padding(30.dp, 0.dp))
                                Text(text= "Seguidores", fontSize = 15.sp , modifier= Modifier.padding(0.dp, 15.dp))
                            }
                        }
                    }
                }

                Row(modifier = Modifier.padding(7.dp).background(color= Color.White).fillMaxWidth()){
                    Text(text = "AAAAAAAAAAAAAAAAAAAA")

                }
        }
    }
}