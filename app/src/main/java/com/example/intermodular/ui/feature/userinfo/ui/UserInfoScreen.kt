package com.example.intermodular.ui.feature.userinfo.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.intermodular.core.user.ServerUserManager
import com.example.intermodular.model.Routes
import com.example.intermodular.ui.component.global.ClickableText
import com.example.intermodular.ui.component.global.WikihonkBaseScreen


@Composable
fun UserInfo(userInfoViewModel: UserInfoViewModel, navigationController: NavHostController){

    val user = ServerUserManager.getSelfUserOrNull() ?: return

    WikihonkBaseScreen(
        navigationController = navigationController,
        showBottomBar = true
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .padding(7.dp)
                    .fillMaxWidth()
            ) {
                Column() {
                    Row(
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxWidth(), horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            bitmap = user.profilePicture,
                            contentDescription = user.username,
                            modifier = Modifier
                                .clip(CircleShape)
                                .border(0.dp, Color.Transparent, CircleShape)
                                .size(100.dp),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Row(
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxWidth(), horizontalArrangement = Arrangement.Center
                    ) {
                        Box() {
                            Text(text = user.username, fontSize = 23.sp)
                        }
                    }

                    Row(
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxWidth(), horizontalArrangement = Arrangement.Center
                    ) {
                        Box() {
                            ClickableText(text = "Cerrar sesión", fontSize = 18.sp , fontWeight = FontWeight.Normal, underlined = true) {
                                userInfoViewModel.logOut()
                                navigationController.navigate(Routes.LoginScreen.route)
                            }
                        }
                    }

                    Row(
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxWidth(), horizontalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier.padding(10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = user.bio.ifBlank { " " },
                                fontSize = 15.sp,
                                modifier = Modifier.padding(30.dp, 0.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            Row(modifier = Modifier
                .padding(7.dp)
                .fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    ClickableText(
                        text = "Mis rutas",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        underlined = false
                    ){
                        navigationController.navigate(Routes.CreatedScreen.route)
                    }
                }
            }

            Row(modifier = Modifier
                .padding(7.dp)
                .fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    ClickableText(
                        text = "Rutas pendientes",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        underlined = false
                    ) {
                        navigationController.navigate(Routes.ToDoScreen.route)
                    }
                }
            }

            Row(modifier = Modifier
                .padding(7.dp)
                .fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    ClickableText(
                        text = "Rutas favoritas",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        underlined = false
                    ) {
                        navigationController.navigate(Routes.FavScreen.route)
                    }
                }
            }
        }
    }
}