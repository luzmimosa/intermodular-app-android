package com.example.intermodular.ui.feature.home.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.intermodular.core.location.WikihonkLocationManager
import com.example.intermodular.core.route.ServerRouteManager
import com.example.intermodular.core.route.model.Route
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val MAX_HOME_ROUTES = 10

    val _routes = MutableLiveData<Array<Route>>()
    val routes: LiveData<Array<Route>> = _routes

    fun refreshRoutes() {
        viewModelScope.launch {
            fetchRoutes()
        }
    }

    suspend fun fetchRoutes() {

        val newRoutes = mutableListOf<Route>()

        Log.i("HomeViewModel", "Adding nearby routes")
        newRoutes.addAll(
            ServerRouteManager.getRoutesByLocation(
                WikihonkLocationManager.userLocation.value?.latitude ?: 0.0,
                WikihonkLocationManager.userLocation.value?.longitude ?: 0.0,
                30,
                MAX_HOME_ROUTES
            )
        )
        Log.i("HomeViewModel", "Added ${newRoutes.size} nearby routes")

        Log.i("HomeViewModel", "Adding random routes")
        newRoutes.addAll(ServerRouteManager.getRandomRoutes(MAX_HOME_ROUTES - newRoutes.size))
        Log.i("HomeViewModel", "Routes are now ${newRoutes.size}")

        _routes.value = newRoutes.toTypedArray()
    }

    fun requestMoreRoutes() {
        // TODO
    }

}