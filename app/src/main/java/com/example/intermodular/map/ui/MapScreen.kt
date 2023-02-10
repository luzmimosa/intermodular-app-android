package com.example.intermodular.map.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapScreen(mapViewModel: MapViewModel, navigationController: NavController){
    val localizacion = LatLng(38.55359897196608, -0.12057169825429333)
    val cameraPositionState= rememberCameraPositionState{
        position = CameraPosition.fromLatLngZoom(localizacion, 13f)
    }

    val route by mapViewModel.route.observeAsState(null)

    Box(modifier= Modifier
        .fillMaxSize()
        .padding(8.dp)
    ){
        if (route != null) {
            GoogleMap(
                modifier= Modifier.fillMaxSize(),
                cameraPositionState= CameraPositionState(),

                ){
                Polyline(
                    points = if (route == null) arrayListOf() else route!!.locations.map { LatLng(it.latitude, it.longitude) },
                    width= 10f,
                    color= Color.Red
                )
            }
        }
    }
}