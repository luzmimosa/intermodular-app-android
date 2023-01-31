package com.example.intermodular

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.intermodular.home.ui.Home
import com.example.intermodular.home.ui.HomeViewModel
import com.example.intermodular.login.ui.Login
import com.example.intermodular.login.ui.LoginViewModel
import com.example.intermodular.model.Routes

@Composable
fun CustomNavigator(){
    val loginviewmodel= LoginViewModel()
    val homeviewmodel= HomeViewModel()

    val navigationController = rememberNavController()

    NavHost(navController= navigationController, startDestination= Routes.LoginScreen.route){
        composable(route = Routes.LoginScreen.route){
            Login(loginviewmodel, navigationController)
        }

        composable(route= Routes.HomeScreen.route){
            Home(homeviewmodel)
        }
    }

}