package com.example.intermodular.ui.feature.map.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.intermodular.core.route.ServerRouteManager
import com.example.intermodular.core.route.model.Route
import com.example.intermodular.core.route.model.Waypoint
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import kotlinx.coroutines.launch


class MapViewModel(
    val routeID: String?
): ViewModel() {

    val singleMode: Boolean = routeID != null && routeID != "null"

    private val _cameraPosition = MutableLiveData<CameraPositionState>()
    val cameraPosition: LiveData<CameraPositionState> = _cameraPosition

    private val _routes = MutableLiveData<Array<Route>>(arrayOf())
    val routes: LiveData<Array<Route>> = _routes

    private val _focusedWaypoint = MutableLiveData<Waypoint>()
    val focusedWaypoint: LiveData<Waypoint> = _focusedWaypoint

    fun setupMap() {
        if (singleMode) {
            loadSingleRoute()
        } else {
            loadAllRoutes()
        }
    }

    private fun loadSingleRoute() {
        viewModelScope.launch {
            val route = ServerRouteManager.getRouteByID(routeID!!) ?: return@launch
            _routes.value = arrayOf(route)
            _cameraPosition.value = CameraPositionState(
                CameraPosition.fromLatLngZoom(
                    LatLng(
                        route.locations.first().latitude,
                        route.locations.first().longitude
                    ),
                    15f
                )
            )
        }
    }

    private fun loadAllRoutes() {
        _routes.value = ServerRouteManager.getRouteCache().toTypedArray()
    }

    fun handleWaypointClick(waypoint: Waypoint) {
        _focusedWaypoint.value = waypoint
    }

    fun closeWaypoint() {
        _focusedWaypoint.value = null
    }

}
