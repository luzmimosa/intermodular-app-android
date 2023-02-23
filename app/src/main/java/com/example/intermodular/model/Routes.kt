package com.example.intermodular.model

sealed class Routes (val route: String){
    object LoginScreen: Routes("login")
    object HomeScreen: Routes("home")
    object RegisterScreen: Routes("registro")
    object UserInfoScreen: Routes("userinfo")
    object FavScreen: Routes("favoritos")
    object RutaNuevaScreen: Routes("rutanueva")

    object MapScreen: Routes("mapa/{focusedRouteID}") {
        fun route(focusedRouteID: String? = null): String {
            return "mapa/${focusedRouteID ?: "null"}"
        }
    }
    object InfoRuta: Routes("inforuta/{routeID}") {
        fun route(routeID: String): String {
            return "inforuta/$routeID"
        }
    }
}