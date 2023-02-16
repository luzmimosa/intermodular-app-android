package com.example.intermodular.ui.feature.rutanueva.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.intermodular.R
import com.example.intermodular.ui.component.global.ClickableText
import com.example.intermodular.ui.component.global.WikihonkBottomBar
import com.example.intermodular.ui.component.global.WikihonkTopBar

@Preview
@Composable
fun RutaNueva(rutanuevaviewmodel: RutaNuevaViewModel= RutaNuevaViewModel(), navigationController: NavHostController= rememberNavController()){

    val nombre: String //by RutaNuevaViewModel.nombre.observeAsState(initial= "")
    val descripcion: String
    val dificultad: String


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
                    .paint(painterResource(id = R.drawable.black))){

                }

                Row(){
                    Box(modifier= Modifier.padding(115.dp, 10.dp, 10.dp, 10.dp)){
                        ClickableText(text = "Añadir imagen", fontSize = 20.sp, fontWeight = FontWeight.Normal, underlined = true) {
                            //añadir una imagen de la galeria o que haya subida al servidor
                        }
                    }
                }

                Row(){

                }
            }
        }
    }
}