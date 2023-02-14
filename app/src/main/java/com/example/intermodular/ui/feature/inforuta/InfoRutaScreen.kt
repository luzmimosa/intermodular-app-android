package com.example.intermodular.ui.feature.inforuta

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.intermodular.R
import com.example.intermodular.model.Routes
import com.example.intermodular.ui.component.global.*

@Preview
@Composable
fun InfoRuta(infoRutaViewModel: InfoRutaViewModel= InfoRutaViewModel(), navigationController: NavHostController= rememberNavController()){
    Scaffold(
        topBar= {
            WikihonkTopBar(navigationController = navigationController)
        },

        bottomBar= {
            WikihonkBottomBar(navigationController)
        }
    ) {
        Column(modifier= Modifier
            .fillMaxSize()
            .background(color = Color.Gray)){
            Row(modifier= Modifier
                .padding(10.dp, 0.dp)
                .fillMaxWidth()
                .paint(
                    painterResource(id = R.drawable.black)
                    //tiene que ser de esa altura(height) pero se tiene que rellenar de los lados
                )){
                
            }
            
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp, 0.dp)
                .background(color = Color.White)){

                //redondear bordes SOLO LOS DE ABAJO

                Box(modifier = Modifier.padding(10.dp, 20.dp, 10.dp, 10.dp)){
                    ProfilePicture(username = "wpf")
                }

                Box(modifier= Modifier.padding(10.dp)){
                    Text(
                        text = "Nombre de la ruta",
                        fontSize = 20.sp,
                        modifier = Modifier.padding(10.dp, 5.dp, 0.dp, 0.dp)
                    )

                    Text(
                        text = "Nombre de usuario",
                        fontSize = 15.sp,
                        modifier= Modifier.padding(10.dp, 35.dp, 0.dp, 0.dp)
                    )

                    Text(
                        text = "Dificultad: ",
                        fontSize = 15.sp,
                        modifier= Modifier.padding(10.dp, 55.dp, 0.dp, 0.dp)
                    )
                    Text(
                        text = "X",
                        fontSize = 15.sp,
                        modifier= Modifier.padding(10.dp, 75.dp, 0.dp, 0.dp)
                    )

                    Box(modifier = Modifier.padding(10.dp, 100.dp, 0.dp, 0.dp)){
                        ClickableText(text = "Ver ruta", fontSize = 20.sp, fontWeight = FontWeight.Bold, underlined = true) {
                            navigationController.navigate(Routes.MapScreen.route)
                        }
                    }
                }

                Box(modifier= Modifier.padding(5.dp, 75.dp, 10.dp, 5.dp)){
                    IconButton(
                        onClick = { /*marcar en favoritos*/ },
                    ) {
                       Icon(
                           imageVector= Icons.Filled.Favorite,
                           contentDescription= stringResource(id = R.string.info_ruta_favorite),
                           tint= Color.Red,
                           modifier = Modifier.size(50.dp)
                       )
                    }
                }
            }

            Row(modifier= Modifier.fillMaxWidth().padding(5.dp).background(color = Color.White)){
                //redondear bordes
                Text(
                    text = "COMENTARIOS",
                    fontSize = 27.sp,
                    modifier= Modifier.padding(90.dp, 10.dp),
                    fontWeight = FontWeight.Bold
                )
            }

            Row(modifier= Modifier.fillMaxWidth().padding(5.dp).background(color = Color.White)){
                //redondear bordes
                //poner imagen mas grande
                Box(){
                    SmallProfilePicture(username = "wpf")
                }

                Box(modifier= Modifier.padding(10.dp)) {
                    Text(
                        text = "Nombre de usuario",
                        fontSize = 20.sp,
                        modifier = Modifier.padding(10.dp, 5.dp, 0.dp, 0.dp)
                    )

                    Text(
                        text = "Cuerpo del comentario",
                        fontSize = 15.sp,
                        modifier = Modifier.padding(10.dp, 35.dp, 0.dp, 0.dp)
                    )
                }
            }
        }
    }
}

