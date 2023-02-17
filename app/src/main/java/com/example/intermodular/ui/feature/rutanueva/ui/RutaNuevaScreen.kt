package com.example.intermodular.ui.feature.rutanueva.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.intermodular.core.camera.CameraManager
import com.example.intermodular.ui.component.global.ClickableText
import com.example.intermodular.ui.component.global.WikihonkBottomBar
import com.example.intermodular.ui.component.global.WikihonkTopBar

@Composable
fun RutaNueva(
    rutaNuevaViewModel: RutaNuevaViewModel = RutaNuevaViewModel(),
    navigationController: NavHostController = rememberNavController()
){
    val routeMainImage: ImageBitmap? by rutaNuevaViewModel.mainImageBitmap.observeAsState(initial = null)


    Scaffold(
        topBar= {
            WikihonkTopBar(navigationController)
        },

        bottomBar= {
            WikihonkBottomBar(navigationController)
        }
    ) {
        Column(modifier= Modifier
            .fillMaxSize()
            .background(color = Color.Gray)) {
            Column(modifier= Modifier
                .padding(15.dp)
                .background(color = Color.White)) {

                //redondear bordes

                Row(modifier= Modifier
                    .fillMaxWidth()
                ){
                    if (routeMainImage != null) {
                        Image(bitmap = routeMainImage!!, contentDescription = "Imagen de la ruta")
                    }
                }

                Row(){
                    Box(modifier= Modifier.padding(115.dp, 10.dp, 10.dp, 10.dp)){
                        ClickableText(text = "Añadir imagen", fontSize = 20.sp, fontWeight = FontWeight.Normal, underlined = true) {
                            CameraManager.takePicture { image ->
                                rutaNuevaViewModel.setMainImage(image)
                            }
                        }
                    }
                }

                Row(){

                }
            }
        }
    }
}