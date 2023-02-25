package com.example.intermodular.ui.feature.userinfo.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.intermodular.core.user.model.User
import com.example.intermodular.model.Routes
import com.example.intermodular.ui.component.global.ClickableText
import com.example.intermodular.ui.component.global.InputPopup
import com.example.intermodular.ui.component.global.WikihonkBaseScreen


@Composable
fun UserInfo(userInfoViewModel: UserInfoViewModel, navigationController: NavHostController) {

    val displayNamePopupVisible: Boolean by userInfoViewModel.displayNamePopupVisible.observeAsState(false)
    val bioPopupVisible: Boolean by userInfoViewModel.bioPopupVisible.observeAsState(false)

    val viewModelUser: User? by userInfoViewModel.user.observeAsState(userInfoViewModel.loadUser())
    if (viewModelUser == null) return
    val user: User = viewModelUser!!

    WikihonkBaseScreen(
        navigationController = navigationController,
        showBottomBar = true
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
        ) {
            Card(
                modifier = Modifier
                    .padding(17.dp)
                    .border(0.dp, Color.Transparent, RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colors.secondary)
                    .shadow(5.dp, shape = RoundedCornerShape(10.dp))
                    .fillMaxWidth()
            ) {
                Column() {
                    Row (
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxWidth(), horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            bitmap = user.profilePicture,
                            contentDescription = user.displayName,
                            modifier = Modifier
                                .clip(CircleShape)
                                .border(0.dp, Color.Transparent, CircleShape)
                                .size(100.dp)
                                .clickable {
                                    userInfoViewModel.handlePictureRequest()
                                },
                            contentScale = ContentScale.Crop
                        )
                    }

                    Row(
                        modifier = Modifier
                            .padding(5.dp)
                            .clickable {
                                userInfoViewModel.setDisplayNamePopupVisible(true)
                            }
                            .fillMaxWidth(), horizontalArrangement = Arrangement.Center
                    ) {
                        Box() {
                            Text(text = user.displayName, fontSize = 23.sp)
                        }
                    }
                    Row(
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxWidth(), horizontalArrangement = Arrangement.Center
                    ) {
                        Box() {
                            Text(text = "@${user.username}", fontSize = 10.sp)
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
                            modifier = Modifier
                                .padding(10.dp)
                                .clickable {
                                    userInfoViewModel.setBioPopupVisible(true)
                                },
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

            Card(modifier = Modifier
                .padding(17.dp)
                .border(0.dp, Color.Transparent, RoundedCornerShape(10.dp))
                .fillMaxWidth()
                .background(MaterialTheme.colors.secondaryVariant)
                .shadow(5.dp, shape = RoundedCornerShape(10.dp))
                .clickable {
                    navigationController.navigate(Routes.CreatedScreen.route)
                }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Mis rutas",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Card(modifier = Modifier
                .padding(17.dp)
                .border(0.dp, Color.Transparent, RoundedCornerShape(10.dp))
                .fillMaxWidth()
                .background(MaterialTheme.colors.secondaryVariant)
                .shadow(5.dp, shape = RoundedCornerShape(10.dp))
                .clickable {
                    navigationController.navigate(Routes.ToDoScreen.route)
                }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Rutas pendientes",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Card(modifier = Modifier
                .padding(17.dp)
                .border(0.dp, Color.Transparent, RoundedCornerShape(10.dp))
                .fillMaxWidth()
                .background(MaterialTheme.colors.secondaryVariant)
                .shadow(5.dp, shape = RoundedCornerShape(10.dp))
                .clickable {
                    navigationController.navigate(Routes.FavScreen.route)
                }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Rutas favoritas",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        if (displayNamePopupVisible) {
            InputPopup(
                message = "Introduce tu nuevo nombre de usuario",
                label = "Nombre de usuario",
                onDismiss = {
                    userInfoViewModel.setDisplayNamePopupVisible(false)
                },
                submitButtonLabel = "Cambiar nombre",
            ) {
                userInfoViewModel.handleNameRequest(it)
                userInfoViewModel.setDisplayNamePopupVisible(false)
            }
        }

        if (bioPopupVisible) {
            InputPopup(
                message = "Introduce tu nueva biografía",
                label = "Biografía",
                onDismiss = {
                    userInfoViewModel.setBioPopupVisible(false)
                },
                submitButtonLabel = "Cambiar biografía",
            ) {
                userInfoViewModel.handleBioRequest(it)
                userInfoViewModel.setBioPopupVisible(false)
            }
        }

    }
}