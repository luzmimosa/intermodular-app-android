package com.example.intermodular.ui.feature.login.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.example.intermodular.R
import com.example.intermodular.model.Routes


@Composable
fun Login(loginViewModel: LoginViewModel, navController: NavHostController){
    val email: String by loginViewModel.email.observeAsState(initial = "")
    val password: String by loginViewModel.password.observeAsState(initial = "")
    val isLoginEnabled: Boolean by loginViewModel.isButtonLoginEnable.observeAsState(initial = false)


    Column(
        modifier= Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
            Box(
                modifier= Modifier
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

                    Row(){
                        Username(email){
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

                    Row(modifier = Modifier.align(Alignment.CenterHorizontally)){
                        BotonLogin(isLoginEnabled, loginViewModel, navController)
                    }

                    Row(){
                        Spacer(modifier= Modifier.size(30 .dp))
                    }

                    Row(modifier= Modifier.align(Alignment.CenterHorizontally)){
                        Registrate(navController)
                    }

                    Row(){
                        Spacer(modifier= Modifier.size(15.dp))
                    }

                }
            }
        }
}

@Composable
fun Username(email: String, onTextChanged: (String) -> Unit){

    TextField(
        onValueChange= { onTextChanged(it)},
        value= email,
        singleLine= true,
        label= { Text(stringResource(id = R.string.login_input_description_username))},
        keyboardOptions= KeyboardOptions(keyboardType= KeyboardType.Email),
    )
}

@Composable
fun Contrasena(password: String, onTextChanged: (String) -> Unit){

    TextField(
        onValueChange = { onTextChanged(it)},

        value= password,
        label= {Text(stringResource(id = R.string.login_input_description_password))},
        singleLine= true,
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType= KeyboardType.Password),
    )
}

@Composable
fun Registrate(navigationController: NavHostController){

    Text(
        text= stringResource(id = R.string.login_button_noaccount),
        fontSize = 12.sp,
        fontWeight= FontWeight.Bold,
        modifier= Modifier.clickable(
            onClick= {
                navigationController.navigate(Routes.RegisterScreen.route)
            }),
        style= TextStyle(textDecoration= TextDecoration.Underline)
    )

}

@Composable
fun RecuperarContra(modifier: Modifier){
    var dialogState by remember{ mutableStateOf(false) }
    Text(
        text= stringResource(id = R.string.login_button_forgotpassword),
        fontSize = 12.sp,
        fontWeight= FontWeight.Bold,
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
        Dialog(
            onDismissRequest = { onDismiss() },
            properties = DialogProperties(
                dismissOnClickOutside = true,
                dismissOnBackPress = true
            )
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(MaterialTheme.colors.surface)
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Row(modifier = Modifier.padding(8.dp)) {
                        Text(text = stringResource(id = R.string.login_popup_changepassword_title), textAlign = TextAlign.Center)
                    }

                    Row(modifier = Modifier.padding(8.dp)) {
                        var text by remember { mutableStateOf("") }
                        TextField(
                            value = text,
                            onValueChange = { text = it },
                            label = { Text(text = stringResource(id = R.string.login_popup_changepassword_field_email)) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                        )
                    }

                    Row(modifier = Modifier.padding(8.dp)) {
                        val context = LocalContext.current
                        Button(
                            onClick = {onDismiss()},
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp),
                        ) {

                            Text(
                                text = stringResource(id = R.string.login_popup_changepassword_button_send)
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
        onClick= { loginViewModel.onButtonLoginPress(navController) }
    ){
        Text(text= stringResource(id = R.string.login_button_submit))

    }
}