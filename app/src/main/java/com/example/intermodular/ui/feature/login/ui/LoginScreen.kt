package com.example.intermodular.ui.feature.login.ui

import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
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
import com.example.intermodular.ui.component.*


@Composable
fun Login(loginViewModel: LoginViewModel, navController: NavHostController){
    val username: String by loginViewModel.username.observeAsState(initial = "")
    val password: String by loginViewModel.password.observeAsState(initial = "")
    val isLoginEnabled: Boolean by loginViewModel.isButtonLoginEnable.observeAsState(initial = false)

    val isLoading: Boolean by loginViewModel.isLoading.observeAsState(initial = false)

    val errorPopupVisible: Boolean by loginViewModel.errorPopupVisible.observeAsState(initial = false)
    val errorPopupMessageResourceID: Int by loginViewModel.errorPopupMessageResourceID.observeAsState(initial = R.string.unknown_error_message)

    Column(
        modifier= Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.app_background),
                contentScale = ContentScale.FillHeight
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.background.copy(alpha = 0.8f))
        ) {
            Box(
                modifier = Modifier
                    .padding(20.dp)
            ) {
                Column {
                    Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                        ProfilePicture(username = null)
                    }

                    PredefinedSpacer()

                    Row() {
                        TextField(
                            onValueChange = {
                                loginViewModel.onLoginChanged(
                                    username = it,
                                    password = password
                                )
                            },
                            value = username,
                            singleLine = true,
                            label = { Text(stringResource(id = R.string.login_input_description_username)) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        )
                    }

                    PredefinedSpacer()

                    Row() {
                        TextField(
                            onValueChange = {
                                loginViewModel.onLoginChanged(
                                    username = username,
                                    password = it
                                )
                            },
                            value = password,
                            label = { Text(stringResource(id = R.string.login_input_description_password)) },
                            singleLine = true,
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        )
                    }

                    PredefinedSpacer()

                    Row() {
                        PasswordRecovery()
                    }

                    PredefinedSpacer()

                    Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                        Button(
                            onClick = { loginViewModel.onButtonLoginPress(navController) },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colors.primary,
                                contentColor = MaterialTheme.colors.onPrimary,
                                disabledBackgroundColor = MaterialTheme.colors.primaryVariant,
                                disabledContentColor = MaterialTheme.colors.onPrimary
                            ),
                            enabled = isLoginEnabled,
                        ) {
                            Text(text = stringResource(id = R.string.login_button_submit))

                        }
                    }

                    PredefinedSpacer()

                    Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                        ClickableText(text = stringResource(id = R.string.login_button_noaccount)) {
                            navController.navigate(Routes.RegisterScreen.route)
                        }
                    }

                    PredefinedSpacer()

                }
            }
        }
    }

    if (isLoading){
        LoadingPopup(stringResource(id = R.string.login_popup_loading_message))
    }

    if (errorPopupVisible) {
        ErrorPopup(
            message = stringResource(id = errorPopupMessageResourceID),
            buttonLabel = stringResource(id = R.string.global_popup_accept)
        ) {
            loginViewModel.closeErrorPopup()
        }
    }
}

@Composable
fun PasswordRecovery(){
    var dialogState by remember { mutableStateOf(false) }
    var confirmationState by remember { mutableStateOf(false)}

    Text(
        text= stringResource(id = R.string.login_button_forgotpassword),
        fontSize = 12.sp,
        fontWeight= FontWeight.Bold,
        modifier= Modifier.clickable(
            onClick= {
                dialogState = true
            }),
        style= TextStyle(textDecoration= TextDecoration.Underline)
    )

    if(dialogState){
        InputPopup(
            message = "Introduce tu email y te enviaremos un correo para recuperar tu contraseña",
            label = "Email",
            submitButtonLabel = "Recuperar contraseña",
            canSubmit = {
                Patterns.EMAIL_ADDRESS.matcher(it).matches()
            },
            onDismiss = {
                dialogState = false
            }
        ) {
            confirmationState = true
            dialogState = false
        }
    }

    if (confirmationState){
        SimplePopup(message = "Si el correo es correcto, te hemos enviado un correo para recuperar tu contraseña") {
            confirmationState = false
        }
    }

}