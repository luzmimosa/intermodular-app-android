package com.example.intermodular

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.intermodular.core.authentication.AuthenticationTokenManager
import com.example.intermodular.model.Routes
import com.example.intermodular.ui.feature.home.ui.Home
import com.example.intermodular.ui.feature.home.ui.HomeViewModel
import com.example.intermodular.ui.feature.home.ui.RouteClassification
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
fun CustomNavigator(context: MainActivity) {
    var alreadyEnteredApplication = false

    val mainHomeViewModel = HomeViewModel(RouteClassification.ALL)
    val loginviewmodel = LoginViewModel(context)
    val registerviewmodel = RegisterViewModel(context)
    val userinfoviewmodel = UserInfoViewModel(context)

    val navigationController = rememberNavController()

    var loginSuccess = false

    navigationController.addOnDestinationChangedListener { navController, destination, bundle ->
        Log.i("---------------Navigator", "Navigating to ${destination.route}")
        if (
            destination.route == Routes.LoginScreen.route
            && alreadyEnteredApplication
        ) {
            if (AuthenticationTokenManager.verifyToken(context)) {
                context.finish()
            }
        }
    }

    NavHost(
        navController = navigationController,
        startDestination = if (AuthenticationTokenManager.verifyToken(context)) {
            Routes.HomeScreen.route
        } else {
            Routes.LoginScreen.route
        }
    ){
        composable(route = Routes.LoginScreen.route){
            Login(
                loginviewmodel,
                navigationController,

                ) {
                if (!loginSuccess) {
                    loginSuccess = true
                    navigationController.navigate(Routes.HomeScreen.route)
                }
            }
        }


        composable(route= Routes.HomeScreen.route){
            Home(mainHomeViewModel, navigationController)
        }

        composable(route= Routes.FavScreen.route) {
            Home(HomeViewModel(RouteClassification.FAVOURITE), navigationController)
        }

        composable(route= Routes.ToDoScreen.route) {
            Home(HomeViewModel(RouteClassification.TODO), navigationController)

        }

        composable(route= Routes.CreatedScreen.route) {
            Home(HomeViewModel(RouteClassification.CREATED), navigationController)

        }

        composable(route= Routes.RegisterScreen.route){
            Registro(registerviewmodel, navigationController)
        }

        composable(route= Routes.UserInfoScreen.route){
            UserInfo(userinfoviewmodel, navigationController)
        }

        composable(
            route = Routes.MapScreen.route,
            arguments = listOf(
                navArgument("focusedRouteID") {
                    type = NavType.StringType
                    this.nullable = true
                    defaultValue = null
                }
            )
        ){

            val routeID = it.arguments?.getString("focusedRouteID")

            val viewModel = MapViewModel(routeID)

            MapScreen(
                viewModel,
                navigationController

            )
        }

        composable(route= Routes.RutaNuevaScreen.route){
            RutaNueva(RutaNuevaViewModel(navigationController), navigationController)
        }

        composable(
            route = Routes.InfoRuta.route,
            arguments = listOf(
                navArgument("routeID") {
                    type = NavType.StringType
                    defaultValue = "undefined"
                }
            )
        ){

            val routeID = it.arguments?.getString("routeID") ?: "undefined"
            InfoRuta(
                infoRutaViewModel = InfoRutaViewModel(routeID, navigationController),
                navigationController = navigationController,
            )
        }
    }

}