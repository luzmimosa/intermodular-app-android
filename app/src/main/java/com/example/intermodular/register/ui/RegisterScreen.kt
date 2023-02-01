package com.example.intermodular.register.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.intermodular.R
import com.example.intermodular.model.Routes
import com.example.intermodular.ui.theme.verde1
import com.example.intermodular.ui.theme.verde2
import com.example.intermodular.ui.theme.verde3


@Composable
fun Registro(registerViewModel: RegisterViewModel, navController: NavHostController){

    val email: String by registerViewModel.email.observeAsState(initial = "")
    val password: String by registerViewModel.password.observeAsState(initial = "")
    val isRegisterEnabled: Boolean by registerViewModel.isButtonRegisterEnable.observeAsState(initial = false)
    val nombre: String by registerViewModel.nombre.observeAsState(initial= "")
    val user: String by registerViewModel.user.observeAsState(initial= "")


    Column(
        modifier= Modifier
            .background(color = verde2)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Box(
            modifier= Modifier
                .background(color = verde1)
                .padding(20.dp)

        ){
            Column(){
                Row(modifier = Modifier.align(Alignment.CenterHorizontally)){
                    Image(
                        painter = painterResource(id = R.drawable.black),
                        contentDescription = "imagen 1",
                        modifier= Modifier
                            .clip(CircleShape)
                            .border(0.dp, Color.Transparent, CircleShape)
                            .size(100.dp)
                    )
                }

                Row(){
                    Spacer(modifier= Modifier.size(15.dp))
                }

                Row {
                    Text(
                        text = "Registro",
                        /*Modifier.padding(horizontal= 8.dp),
                        fontSize = 20.dp,
                        fontWeight = FontWeight.Bold*/)
                }

                Row(){
                    Spacer(modifier= Modifier.size(15.dp))
                }

                Row(){
                    Nombre(nombre){

                    }
                }

                Row(){
                    Spacer(modifier= Modifier.size(15.dp))
                }

                Row(){
                    User(user){

                    }
                }

                Row(){
                    Spacer(modifier= Modifier.size(15.dp))
                }

                Row(){
                    Email(email){

                    }
                }

                Row(){
                    Spacer(modifier= Modifier.size(15.dp))
                }

                Row(){
                    Contrasena(password){

                    }
                }

                Row(){
                    Spacer(modifier= Modifier.size(15.dp))
                }

                Row(){
                    RepiteContrasena(password){

                    }
                }

                Row(){
                    Spacer(modifier= Modifier.size(15.dp))
                }

                Row(modifier = Modifier.align(Alignment.CenterHorizontally)){
                    BotonRegistro(isRegisterEnabled, registerViewModel, navController)
                }

                Row(){
                    Spacer(modifier= Modifier.size(30 .dp))
                }

                Row(modifier= Modifier.align(Alignment.CenterHorizontally)){
                    IniciaSesion(navController)
                }

                Row(){
                    Spacer(modifier= Modifier.size(15.dp))
                }

            }
        }
    }
}


@Composable
fun Nombre(nombre: String, onTextChanged: (String) -> Unit) {
    TextField(
        onValueChange= { onTextChanged(it)},
        value= nombre,
        singleLine= true,
        label= { Text("Email")},
        keyboardOptions= KeyboardOptions(keyboardType= KeyboardType.Email),
        colors= TextFieldDefaults.textFieldColors(
            textColor= Color.Black,
            backgroundColor = Color.White

        )
    )
}

@Composable
fun User(user: String, onTextChanged: (String) -> Unit) {
    TextField(
        onValueChange= { onTextChanged(it)},
        value= user,
        singleLine= true,
        label= { Text("Email")},
        keyboardOptions= KeyboardOptions(keyboardType= KeyboardType.Email),
        colors= TextFieldDefaults.textFieldColors(
            textColor= Color.Black,
            backgroundColor = Color.White

        )
    )
}


@Composable
fun Email(email: String, onTextChanged: (String) -> Unit){

    TextField(
        onValueChange= { onTextChanged(it)},
        value= email,
        singleLine= true,
        label= { Text("Email")},
        keyboardOptions= KeyboardOptions(keyboardType= KeyboardType.Email),
        colors= TextFieldDefaults.textFieldColors(
            textColor= Color.Black,
            backgroundColor = Color.White

        )
    )
}

@Composable
fun Contrasena(password: String, onTextChanged: (String) -> Unit){

    TextField(
        onValueChange = { onTextChanged(it)},

        value= password,
        label= {Text("Contraseña")},
        singleLine= true,
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType= KeyboardType.Password),
        colors= TextFieldDefaults.textFieldColors(
            textColor= Color.Black,
            backgroundColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor= Color.Transparent
        )
    )
}

@Composable
fun RepiteContrasena(password: String, onTextChanged: (String) -> Unit) {

    TextField(
        onValueChange = { onTextChanged(it)},

        value= password,
        label= {Text("Repite la contraseña")},
        singleLine= true,
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType= KeyboardType.Password),
        colors= TextFieldDefaults.textFieldColors(
            textColor= Color.Black,
            backgroundColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor= Color.Transparent
        )
    )
}

@Composable
fun IniciaSesion(navController: NavHostController){

    Text(
        text= "¿Ya tienes una cuenta? Inicia sesión",
        fontSize = 12.sp,
        fontWeight= FontWeight.Bold,
        color= Color.White,
        modifier= Modifier.clickable(
            onClick= {
                navController.navigate(Routes.LoginScreen.route)
            }),
        style= TextStyle(textDecoration= TextDecoration.Underline)
    )

}

@Composable
fun BotonRegistro(registerEnable:Boolean, registerViewModel: RegisterViewModel, navController: NavHostController){
    Button(
        onClick= { registerViewModel.onButtonRegisterPress(navController) },
        colors= ButtonDefaults.buttonColors(
            backgroundColor= verde3,
            disabledBackgroundColor = verde1,
        )
    ){
        Text(text= "Entrar", color= Color.White)
    }
}