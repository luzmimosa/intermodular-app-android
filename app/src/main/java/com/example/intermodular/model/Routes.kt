package com.example.intermodular.model

sealed class Routes (val route: String){
    object LoginScreen: Routes("login")
    object RegisterScreen: Routes("registro")

    object HomeScreen: Routes("home")
    object FavScreen: Routes("favoritos")
    object ToDoScreen: Routes("todo")
    object CreatedScreen: Routes("creadas")

    object UserInfoScreen: Routes("userinfo")

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