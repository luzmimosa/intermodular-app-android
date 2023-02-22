package com.example.intermodular.ui.feature.map.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.intermodular.core.location.WikihonkLocationManager
import com.example.intermodular.core.route.ServerRouteManager
import com.example.intermodular.core.route.model.Route
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch


class MapViewModel(
    val routeID: String?
): ViewModel() {

    private val _mapLocation = MutableLiveData<LatLng>()
    val mapLocation: LiveData<LatLng> = _mapLocation

    private val _routes = MutableLiveData<List<Route>>()
    val routes: LiveData<List<Route>> = _routes

    private val routeQueue = mutableListOf<Route>()

    fun setupMap() {
        _mapLocation.value = LatLng(
            WikihonkLocationManager.userLocation.value?.latitude ?: 0.0,
            WikihonkLocationManager.userLocation.value?.longitude ?: 0.0
        )

        viewModelScope.launch {
            loadRoutes()
        }
    }

    private suspend fun loadRoutes() {
        if (routeID != null) {
            loadSpecificRoute()
        } else {
            loadNearbyRoutes()
            loadRandomRoutes()
        }
    }

    private suspend fun loadSpecificRoute() {
        if (routeID != null) {
            val route = ServerRouteManager.getRouteByID(routeID) ?: return

            _routes.value = listOf(route)
            _mapLocation.value = LatLng(
                route.locations.first().latitude,
                route.locations.first().longitude
            )

        }
    }

    private suspend fun loadNearbyRoutes() {
        ServerRouteManager.provideRoutesByLocation(
            WikihonkLocationManager.userLocation.value?.latitude ?: 0.0,
            WikihonkLocationManager.userLocation.value?.longitude ?: 0.0,
        ) {
            routeQueue.add(it)
            loadRoutesFromQueue()
        }
    }

    private suspend fun loadRandomRoutes() {
        ServerRouteManager.provideRandomRoutes {
            routeQueue.add(it)
            loadRoutesFromQueue()
        }
    }

    private fun loadRoutesFromQueue() {
        _routes.value = _routes.value?.plus(routeQueue) ?: routeQueue
    }
}