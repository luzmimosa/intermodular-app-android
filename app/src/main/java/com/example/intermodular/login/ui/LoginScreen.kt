package com.example.intermodular.login.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.example.intermodular.R


@Composable
fun Login(loginViewModel: LoginViewModel, navController: NavHostController){
    val email: String by loginViewModel.email.observeAsState(initial = "")
    val password: String by loginViewModel.password.observeAsState(initial = "")
    val isLoginEnabled: Boolean by loginViewModel.isButtonLoginEnable.observeAsState(initial = false)


    Column(
        modifier= Modifier.background(color= Color(R.color.verde2))
    ){
        Row(){

            Box(
                modifier= Modifier.background(color= Color(R.color.verde1))
            ){
                Column(){
                    Row(){
                        Spacer(modifier= Modifier.size(15.dp))
                    }

                    Row(){
                        Email(email){
                            loginViewModel.onLoginChanged(email= it, password= password)
                        }
                    }

                    Row(){
                        Spacer(modifier= Modifier.size(15.dp))
                    }

                    Row(){
                        Contrasena(password){
                            loginViewModel.onLoginChanged(email= email, password= it)
                        }
                    }

                    Row(){
                        Spacer(modifier= Modifier.size(15.dp))
                    }

                    Row(){
                        RecuperarContra(Modifier.align(Alignment.Bottom))
                    }

                    Row(){
                        Spacer(modifier= Modifier.size(15.dp))
                    }

                    Row(){
                        BotonLogin(isLoginEnabled, loginViewModel, navController)
                    }

                }
            }
        }
    }
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
            textColor= Color(R.color.black),
            backgroundColor = Color(R.color.white),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor= Color.Transparent

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
        keyboardOptions = KeyboardOptions(keyboardType= KeyboardType.Password),
        colors= TextFieldDefaults.textFieldColors(
            textColor= Color(R.color.black),
            backgroundColor = Color(R.color.white),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor= Color.Transparent

        )
    )
}

@Composable
fun RecuperarContra(modifier: Modifier){
    var dialogState by remember{ mutableStateOf(false) }
    Text(
        text= "¿Olvidaste la contraseña? Recuperala",
        fontSize = 12.sp,
        fontWeight= FontWeight.Bold,
        color= Color(0xFFFFFFFFF),
        modifier= Modifier.clickable(
            onClick= {
                dialogState= !dialogState
            }),
        style= TextStyle(textDecoration= TextDecoration.Underline)
    )

    if(dialogState){
        dialogContra(dialogState= true, onDismiss= {dialogState= false})
    }

}

@Composable
fun dialogContra(dialogState: Boolean, onDismiss:() -> Unit){
    var email by remember { mutableStateOf("") }

    if(dialogState){
        Dialog(onDismissRequest = { onDismiss() }, properties = DialogProperties(dismissOnClickOutside = true, dismissOnBackPress = false)) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.background(color = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Row(modifier = Modifier.padding(8.dp)) {
                        Text(text = "Introduce tu correo para cambiar la contraseña", textAlign = TextAlign.Center)
                    }

                    Row(modifier = Modifier.padding(8.dp)) {
                        var text by remember { mutableStateOf("") }
                        TextField(
                            value = text,
                            onValueChange = { text = it },
                            label = { Text(text = "Email") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                        )
                    }

                    Row(modifier = Modifier.padding(8.dp)) {
                        val context = LocalContext.current
                        Button(
                            onClick = {onDismiss()},
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(R.color.verde4)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp),
                        ) {

                            Text(
                                text = "Entrar",
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BotonLogin(loginEnable:Boolean, loginViewModel: LoginViewModel, navController: NavHostController){
    Button(
        onClick= { loginViewModel.onButtonLoginPress(navController) },
        colors= ButtonDefaults.buttonColors(
            backgroundColor= Color(R.color.verde1),
            disabledBackgroundColor = Color(R.color.verde4),
            contentColor = Color(R.color.white),
            disabledContentColor = Color(R.color.white)
        )
    ){
        Text(text= "Entrar")

    }
}