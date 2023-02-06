package com.example.intermodular.model

sealed class Routes (val route: String){
    object LoginScreen: Routes("login")
    object HomeScreen: Routes("home")
    object RegisterScreen: Routes("registro")
    object UserInfoScreen: Routes("userinfo")
    object FavScreen: Routes("favoritos")
    object MapScreen: Routes("mapa")
}