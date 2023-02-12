package com.example.intermodular.ui.feature.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.example.intermodular.core.route.model.GpsMeasure
import com.example.intermodular.core.route.model.Route
import com.example.intermodular.core.route.model.RouteDifficulty
import com.example.intermodular.core.route.model.RouteType
import com.example.intermodular.ui.component.global.WikihonkBottomBar
import com.example.intermodular.ui.component.global.WikihonkTopBar
import com.example.intermodular.ui.component.route.RouteCard
import java.time.LocalDateTime

@Composable
fun Home(homeviewmodel : HomeViewModel, navigationController: NavHostController){

    Scaffold(
        topBar = {
            WikihonkTopBar(navigationController)
        },

        bottomBar = {
            WikihonkBottomBar(navigationController)
        },
    ){

        Column(modifier= Modifier.background(color= Color.Gray).fillMaxSize()) {
            Row(){
                RouteCard(
                    route = Route(
                        uid = "nbjhfdbghjbjghbshjngh",
                        name = "Ruta de la seda",
                        description = "Una ruta muy bonita por la costa de Galicia, con vistas al mar y a la montaña. Es una ruta muy bonita y muy recomendable para hacer en familia. Se puede hacer andando o corriendo. Además, se puede hacer en bicicleta, si se es atrevido. Naranja de Valencia.",
                        imageID = "uhubnjfbjhbjbdfnjgb43",
                        lengthInKm = 42.75623,
                        locations = arrayOf(
                            GpsMeasure(42.75623, -8.20654),
                            GpsMeasure(42.75823, -8.76654),
                            GpsMeasure(42.76323, -8.91654),
                            GpsMeasure(42.77023, -8.85654),
                        ),
                        types = arrayOf(RouteType.RUNNING, RouteType.WALK, RouteType.BYCICLE),
                        difficulty = RouteDifficulty.MEDIUM,
                        creator = "alpacaBella19",
                        creationDatetime = LocalDateTime.now().minusDays(5)
                    )
                )
            }
        }
    }
}


