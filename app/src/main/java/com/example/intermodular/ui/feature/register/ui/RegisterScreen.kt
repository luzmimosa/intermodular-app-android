package com.example.intermodular.ui.feature.register.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.intermodular.R
import com.example.intermodular.model.Routes
import com.example.intermodular.ui.component.ClickableText
import com.example.intermodular.ui.component.ErrorSupporterTextField
import com.example.intermodular.ui.component.PredefinedSpacer
import com.example.intermodular.ui.component.ProfilePicture


@Composable
fun Registro(
    registerViewModel: RegisterViewModel,
    navController: NavHostController
){

    val displayName: String by registerViewModel.nombre.observeAsState(initial= "")
    val username: String by registerViewModel.user.observeAsState(initial= "")
    val email: String by registerViewModel.email.observeAsState(initial = "")
    val password: String by registerViewModel.password.observeAsState(initial = "")
    val confirmedPassword: String by registerViewModel.repeatedPassword.observeAsState(initial = "")

    val displayNameAlertVisible: Boolean by registerViewModel.nombreAlertVisible.observeAsState(initial = false)
    val usernameAlertVisible: Boolean by registerViewModel.userAlertVisible.observeAsState(initial = false)
    val emailAlertVisible: Boolean by registerViewModel.emailAlertVisible.observeAsState(initial = false)
    val passwordAlertVisible: Boolean by registerViewModel.passwordAlertVisible.observeAsState(initial = false)
    val confirmedPasswordAlertVisible: Boolean by registerViewModel.passwordConfirmAlertVisible.observeAsState(initial = false)

    Column(
        modifier= Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.app_background),
                contentScale = ContentScale.FillHeight
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
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

                    Row {
                        Text(
                            text = stringResource(id = R.string.register_form_title)
                        )
                    }

                    PredefinedSpacer()

                    // Display name
                    Row {
                        ErrorSupporterTextField(
                            label = stringResource(id = R.string.register_field_name),
                            value = displayName,
                            errorVisible = displayNameAlertVisible,
                            errorMessage = stringResource(id = R.string.register_error_name),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                        ) {
                            registerViewModel.onRegisterChanged(
                                email,
                                password,
                                username,
                                it,
                                confirmedPassword
                            )
                        }
                    }

                    PredefinedSpacer()

                    // Username
                    Row {
                        ErrorSupporterTextField(
                            label = stringResource(id = R.string.register_field_username),
                            value = username,
                            errorVisible = usernameAlertVisible,
                            errorMessage = stringResource(id = R.string.register_error_username),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                        ) {
                            registerViewModel.onRegisterChanged(
                                email,
                                password,
                                it,
                                displayName,
                                confirmedPassword
                            )
                        }
                    }

                    PredefinedSpacer()

                    // Email
                    Row {
                        ErrorSupporterTextField(
                            label = stringResource(id = R.string.register_field_email),
                            value = email,
                            errorVisible = emailAlertVisible,
                            errorMessage = stringResource(id = R.string.register_error_email),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                        ) {
                            registerViewModel.onRegisterChanged(
                                it,
                                password,
                                username,
                                displayName,
                                confirmedPassword
                            )
                        }
                    }

                    PredefinedSpacer()

                    // Password
                    Row {
                        ErrorSupporterTextField(
                            label = stringResource(id = R.string.register_field_password),
                            value = password,
                            errorVisible = passwordAlertVisible,
                            errorMessage = stringResource(id = R.string.register_error_password),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            visualTransformation = PasswordVisualTransformation()
                        ) {
                            registerViewModel.onRegisterChanged(
                                email,
                                it,
                                username,
                                displayName,
                                confirmedPassword
                            )
                        }
                    }

                    PredefinedSpacer()

                    // Confirm password
                    Row {
                        ErrorSupporterTextField(
                            label = stringResource(id = R.string.register_field_confirm_password),
                            value = confirmedPassword,
                            errorVisible = confirmedPasswordAlertVisible,
                            errorMessage = stringResource(id = R.string.register_error_password_match),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            visualTransformation = PasswordVisualTransformation()
                        ) {
                            registerViewModel.onRegisterChanged(
                                email,
                                password,
                                username,
                                displayName,
                                it
                            )
                        }
                    }

                    PredefinedSpacer()

                    // Register submit button
                    Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                        Button(
                            onClick = { registerViewModel.onButtonRegisterPress(navController) }
                        ) {
                            Text(text = stringResource(id = R.string.register_button_submit))
                        }
                    }

                    PredefinedSpacer()

                    // Login if already registered
                    Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                        ClickableText(
                            text = stringResource(id = R.string.register_label_already_registered)
                        ) {
                            navController.navigate(Routes.LoginScreen.route)
                        }
                    }

                    PredefinedSpacer()

                }
            }
        }
    }
}