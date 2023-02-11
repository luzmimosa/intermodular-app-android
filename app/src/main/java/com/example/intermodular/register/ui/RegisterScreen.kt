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
import androidx.compose.ui.res.stringResource
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
fun Registro(
    registerViewModel: RegisterViewModel,
    navController: NavHostController
){

    val nombre: String by registerViewModel.nombre.observeAsState(initial= "")
    val user: String by registerViewModel.user.observeAsState(initial= "")
    val email: String by registerViewModel.email.observeAsState(initial = "")
    val password: String by registerViewModel.password.observeAsState(initial = "")
    val repeatedPassword: String by registerViewModel.repeatedPassword.observeAsState(initial = "")

    val nombreAlertVisible: Boolean by registerViewModel.nombreAlertVisible.observeAsState(initial = false)
    val userAlertVisible: Boolean by registerViewModel.userAlertVisible.observeAsState(initial = false)
    val emailAlertVisible: Boolean by registerViewModel.emailAlertVisible.observeAsState(initial = false)
    val passwordAlertVisible: Boolean by registerViewModel.passwordAlertVisible.observeAsState(initial = false)
    val repeatedPasswordAlertVisible: Boolean by registerViewModel.passwordConfirmAlertVisible.observeAsState(initial = false)

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
            Column {
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

                Row {
                    Spacer(modifier= Modifier.size(10.dp))
                }

                Row {
                    Text(
                        text = stringResource(id = R.string.register_form_title)
                    )
                }

                Row(){
                    Spacer(modifier= Modifier.size(10.dp))
                }

                Row(){
                    Nombre(nombre, nombreAlertVisible){
                        registerViewModel.onRegisterChanged(email, password, user, it, repeatedPassword)
                    }
                }

                Row(){
                    Spacer(modifier= Modifier.size(10.dp))
                }

                Row(){
                    User(user, userAlertVisible){
                        registerViewModel.onRegisterChanged(email, password, it, nombre, repeatedPassword)
                    }
                }

                Row(){
                    Spacer(modifier= Modifier.size(10.dp))
                }

                Row(){
                    Email(email, emailAlertVisible){
                        registerViewModel.onRegisterChanged(it, password, user, nombre, repeatedPassword)
                    }
                }

                Row(){
                    Spacer(modifier= Modifier.size(10.dp))
                }

                Row(){
                    Contrasena(password, passwordAlertVisible){
                        registerViewModel.onRegisterChanged(email, it, user, nombre, repeatedPassword)
                    }
                }

                Row(){
                    Spacer(modifier= Modifier.size(10.dp))
                }

                Row(){
                    RepiteContrasena(repeatedPassword, repeatedPasswordAlertVisible) {
                        registerViewModel.onRegisterChanged(email, password, user, nombre, it)
                    }
                }

                Row(){
                    Spacer(modifier= Modifier.size(10.dp))
                }

                Row(modifier = Modifier.align(Alignment.CenterHorizontally)){
                    BotonRegistro(registerViewModel, navController)
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
fun Nombre(value: String, errorVisible: Boolean, onTextChanged: (String) -> Unit) {
    Column {
        Row {
            TextField(
                onValueChange= { onTextChanged(it)},
                value= value,
                singleLine= true,
                label= { Text(stringResource(id = R.string.register_field_name))},
                keyboardOptions= KeyboardOptions(keyboardType= KeyboardType.Email),
                colors= TextFieldDefaults.textFieldColors(
                    textColor= Color.Black,
                    backgroundColor = Color.White

                )
            )
        }
        if (errorVisible) {
            Row (
                modifier = Modifier.padding(0.dp)
                    ) {
                Text(
                    text = stringResource(id = R.string.register_error_name),
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(0.dp)
                )
            }
        }
    }

}

@Composable
fun User(value: String, errorVisible: Boolean, onTextChanged: (String) -> Unit) {

    Column {
        Row {
            TextField(
                onValueChange= { onTextChanged(it)},
                value = value,
                singleLine= true,
                label= { Text(stringResource(id = R.string.register_field_username))},
                keyboardOptions= KeyboardOptions(keyboardType= KeyboardType.Email),
                colors= TextFieldDefaults.textFieldColors(
                    textColor= Color.Black,
                    backgroundColor = Color.White
                )
            )
        }
        if (errorVisible) {
            Row (
                modifier = Modifier.padding(0.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.register_error_username),
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    }
}


@Composable
fun Email(value: String, errorVisible: Boolean, onTextChanged: (String) -> Unit){

    Column {
        Row {
            TextField(
                onValueChange= { onTextChanged(it)},
                value= value,
                singleLine= true,
                label= { Text(stringResource(id = R.string.register_field_email))},
                keyboardOptions= KeyboardOptions(keyboardType= KeyboardType.Email),
                colors= TextFieldDefaults.textFieldColors(
                    textColor= Color.Black,
                    backgroundColor = Color.White

                )
            )
        }
        if (errorVisible) {
            Row (
                modifier = Modifier.padding(0.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.register_error_email),
                    color = Color.Red,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun Contrasena(value: String, errorVisible: Boolean, onTextChanged: (String) -> Unit){

    Column {
        Row {
            TextField(
                onValueChange = { onTextChanged(it)},

                value= value,
                label= {Text(stringResource(id = R.string.register_field_password))},
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
        if (errorVisible) {
            Row (
                modifier = Modifier.padding(0.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.register_error_password),
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    }
}

@Composable
fun RepiteContrasena(value: String, errorVisible: Boolean, onTextChanged: (String) -> Unit) {

    Column {
        Row {
            TextField(
                onValueChange = { onTextChanged(it)},

                value= value,
                label= {Text(stringResource(id = R.string.register_field_confirm_password))},
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
        if (errorVisible) {
            Row {
                Text(
                    text = stringResource(id = R.string.register_error_password_match),
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    }
}

@Composable
fun IniciaSesion(navController: NavHostController){

    Text(
        text= stringResource(id = R.string.register_label_already_registered),
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
fun BotonRegistro(registerViewModel: RegisterViewModel, navController: NavHostController){
    Button(
        onClick= { registerViewModel.onButtonRegisterPress(navController) },
        colors= ButtonDefaults.buttonColors(
            backgroundColor= verde3,
            disabledBackgroundColor = verde1,
        )
    ){
        Text(text= stringResource(id = R.string.register_button_submit), color= Color.White)
    }
}