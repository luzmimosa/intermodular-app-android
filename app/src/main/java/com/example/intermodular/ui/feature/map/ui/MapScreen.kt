package com.example.intermodular.ui.feature.map.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.intermodular.R
import com.example.intermodular.core.route.model.Route
import com.example.intermodular.ui.component.global.WikihonkBottomBar
import com.example.intermodular.ui.component.global.WikihonkTopBar
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.*

@Composable
fun MapScreen(
    mapViewModel: MapViewModel,
    navigationController: NavHostController
){
    val camLocation: LatLng by mapViewModel.mapLocation.observeAsState(LatLng(0.0, 0.0))

    val cameraPositionState= rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(camLocation, 13f)
    }

    val routes: List<Route> by mapViewModel.routes.observeAsState(listOf())

    mapViewModel.setupMap()

    Scaffold(
        topBar= {
            WikihonkTopBar(navigationController)
        },

        bottomBar= {
            WikihonkBottomBar(navigationController)
        }
    ) {
        Box(modifier= Modifier
            .fillMaxSize()
            .padding(8.dp)
        ) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = MapProperties(
                    mapStyleOptions = MapStyleOptions.loadRawResourceStyle(
                        LocalContext.current,
                        if (isSystemInDarkTheme()) R.raw.dark_map_style else R.raw.light_map_style
                    ),
                    isMyLocationEnabled = true
                ),
                uiSettings = MapUiSettings(zoomControlsEnabled = false)
            ) {
                for (route in routes) {
                    Polyline(
                        points = route.locations.map { LatLng(it.latitude, it.longitude) },
                        color = routeIdToColor(route.uid),
                        width = 5f
                    )
                }
            }
        }
    }
}


fun routeIdToColor(routeId: String): Color {
    val hash = routeId.hashCode()
    val r = (hash and 0xFF0000) shr 16
    val g = (hash and 0x00FF00) shr 8
    val b = hash and 0x0000FF
    return Color(r, g, b)
}