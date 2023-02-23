package com.example.intermodular.ui.feature.home.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.intermodular.core.location.WikihonkLocationManager
import com.example.intermodular.core.route.ServerRouteManager
import com.example.intermodular.core.route.model.Route

class HomeViewModel : ViewModel() {

    val _routes = MutableLiveData<Array<Route>>()
    val routes: LiveData<Array<Route>> = _routes

    private val routeQueue = mutableListOf<Route>()

    fun refreshRoutes() {
        checkRouteQueue()
    }

    suspend fun fetchRoutes() {

        ServerRouteManager.provideRoutesByLocation(
            WikihonkLocationManager.userLocation.value?.latitude ?: 0.0,
            WikihonkLocationManager.userLocation.value?.longitude ?: 0.0,
        ) {
            this.addRoute(it)
        }

        ServerRouteManager.provideRandomRoutes {
            this.addRoute(it)
        }

    }

    fun requestMoreRoutes() {
        // TODO request more
    }

    private fun addRoute(route: Route) {
        routeQueue.add(route)
    }

    private fun checkRouteQueue() {
        if (routeQueue.isNotEmpty()) {
            _routes.value = _routes.value?.plus(routeQueue) ?: routeQueue.toTypedArray()

            routeQueue.removeAll { true }
        }
    }

}