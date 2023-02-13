package com.example.intermodular.ui.feature.map.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.intermodular.R
import com.example.intermodular.ui.component.global.WikihonkBottomBar
import com.example.intermodular.ui.component.global.WikihonkTopBar
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.*

@Composable
fun MapScreen(mapViewModel: MapViewModel, navigationController: NavHostController){
    val localizacion = LatLng(38.55359897196608, -0.12057169825429333)
    val cameraPositionState= rememberCameraPositionState{
        position = CameraPosition.fromLatLngZoom(localizacion, 13f)
    }

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
                modifier= Modifier.fillMaxSize(),
                cameraPositionState= CameraPositionState(),
                properties = MapProperties(
                    mapStyleOptions = MapStyleOptions.loadRawResourceStyle(
                        LocalContext.current,
                        if (isSystemInDarkTheme()) R.raw.dark_map_style else R.raw.light_map_style
                    ),
                    isMyLocationEnabled = true,
                ),
                uiSettings = MapUiSettings(zoomControlsEnabled = false)
            ) {
                Polyline(
                    points = listOf(
                        LatLng(38.55359897196608, -0.12057169825429333),
                        LatLng(39.55359897196608, -0.12057169825429333),
                        LatLng(40.55359897196608, -0.12057169825429333),
                        LatLng(41.55359897196608, -0.12057169825429333)
                    ),
                    color= Color.Red
                )
            }
        }
    }
}
