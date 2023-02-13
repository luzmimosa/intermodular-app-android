package com.example.intermodular

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.intermodular.model.Routes
import com.example.intermodular.ui.feature.favoritos.ui.Fav
import com.example.intermodular.ui.feature.favoritos.ui.FavViewModel
import com.example.intermodular.ui.feature.home.ui.Home
import com.example.intermodular.ui.feature.home.ui.HomeViewModel
import com.example.intermodular.ui.feature.inforuta.InfoRuta
import com.example.intermodular.ui.feature.inforuta.InfoRutaViewModel
import com.example.intermodular.ui.feature.login.ui.Login
import com.example.intermodular.ui.feature.login.ui.LoginViewModel
import com.example.intermodular.ui.feature.map.ui.MapScreen
import com.example.intermodular.ui.feature.map.ui.MapViewModel
import com.example.intermodular.ui.feature.register.ui.RegisterViewModel
import com.example.intermodular.ui.feature.register.ui.Registro
import com.example.intermodular.ui.feature.rutanueva.ui.RutaNueva
import com.example.intermodular.ui.feature.rutanueva.ui.RutaNuevaViewModel
import com.example.intermodular.ui.feature.userinfo.ui.UserInfo
import com.example.intermodular.ui.feature.userinfo.ui.UserInfoViewModel

@Composable
fun CustomNavigator(){
    val loginviewmodel= LoginViewModel()
    val homeviewmodel= HomeViewModel()
    val registerviewmodel= RegisterViewModel()
    val userinfoviewmodel= UserInfoViewModel()
    val favviewmodel= FavViewModel()
    val rutanuevaviewmodel= RutaNuevaViewModel()
    val inforutaviewmodel= InfoRutaViewModel()

    val navigationController = rememberNavController()

    NavHost(navController= navigationController, startDestination= Routes.LoginScreen.route){
        composable(route = Routes.LoginScreen.route){
            Login(loginviewmodel, navigationController)
        }

        composable(route= Routes.HomeScreen.route){
            Home(homeviewmodel, navigationController)
        }

        composable(route= Routes.RegisterScreen.route){
            Registro(registerviewmodel, navigationController)
        }

        composable(route= Routes.UserInfoScreen.route){
            UserInfo(/*userinfoviewmodel,*/ navigationController)
        }

        composable(route= Routes.MapScreen.route){
            MapScreen(MapViewModel(), navigationController)
        }

        composable(route= Routes.FavScreen.route){
            Fav(favviewmodel, navigationController)
        }

        composable(route= Routes.RutaNuevaScreen.route){
            RutaNueva(rutanuevaviewmodel, navigationController)
        }

        composable(route= Routes.InfoRuta.route){
            InfoRuta(inforutaviewmodel, navigationController )
        }
    }

}