package com.example.intermodular.ui.feature.map.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.intermodular.R
import com.example.intermodular.core.location.WikihonkLocationManager
import com.example.intermodular.model.Routes
import com.example.intermodular.ui.component.global.WikihonkBaseScreen
import com.example.intermodular.ui.component.route.WaypointPopup
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.*

@Composable
fun MapScreen(
    mapViewModel: MapViewModel,
    navigationController: NavHostController
){
    val cameraPositionState: CameraPositionState by mapViewModel.cameraPosition.observeAsState(
        CameraPositionState(
            CameraPosition.fromLatLngZoom(
                LatLng(
                    WikihonkLocationManager.userLocation.value?.latitude ?: 0.0,
                    WikihonkLocationManager.userLocation.value?.longitude ?: 0.0
                ),
                2f
            )
        )
    )
    val routes by mapViewModel.routes.observeAsState(emptyArray())
    val focusedWaypoint by mapViewModel.focusedWaypoint.observeAsState(null)

    mapViewModel.setupMap()

    WikihonkBaseScreen(
        navigationController = navigationController,
        showBottomBar = !mapViewModel.singleMode
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
                    Marker(
                        state = MarkerState(
                            position = LatLng(route.locations.first().latitude, route.locations.first().longitude),
                        ),
                        title = route.name,
                        snippet = route.description,
                        icon = BitmapDescriptorFactory.defaultMarker(
                            rgbToHue(routeIdToColor(route.uid))
                        ),
                        onClick = {
                            if (!mapViewModel.singleMode) {
                                navigationController.navigate(Routes.InfoRuta.route(route.uid))
                            }
                            false
                        }
                    )
                    Polyline(
                        points = route.locations.map { LatLng(it.latitude, it.longitude) },
                        color = routeIdToColor(route.uid),
                        width = 8f
                    ) {
                        navigationController.navigate(Routes.InfoRuta.route(route.uid))
                    }
                }

                // dibujar waypoints si solo hay una ruta
                if (mapViewModel.singleMode) {
                    val route = routes.firstOrNull() ?: return@GoogleMap

                    for (location in route.locations) {
                        val waypoint = location.waypoint ?: continue

                        Marker(
                            state = MarkerState(
                                position = LatLng(location.latitude, location.longitude),
                            ),
                            title = waypoint.name,
                            snippet = waypoint.description,
                            icon = BitmapDescriptorFactory.defaultMarker(
                                if (isSystemInDarkTheme()) 68f else 281f
                            ),
                            onClick = {
                                mapViewModel.handleWaypointClick(waypoint)
                                false
                            }
                        )
                    }
                }

            }
        }

        if (focusedWaypoint != null) {
            WaypointPopup(
                waypoint = focusedWaypoint!!
            ) {
                mapViewModel.closeWaypoint()
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

fun rgbToHue(color: Color): Float {
    val r = color.red
    val g = color.green
    val b = color.blue
    val max = maxOf(r, g, b)
    val min = minOf(r, g, b)
    val delta = max - min
    val hue = when {
        delta == 0.0f -> 0f
        max == r -> ((g - b) / delta) % 6
        max == g -> ((b - r) / delta) + 2
        else -> ((r - g) / delta) + 4
    }

    return Math.abs((hue * 60) % 360)
}