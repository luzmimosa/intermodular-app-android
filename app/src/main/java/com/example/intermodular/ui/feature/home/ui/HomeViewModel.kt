package com.example.intermodular.ui.feature.home.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.intermodular.core.route.ServerRouteManager
import com.example.intermodular.core.route.model.Route

class HomeViewModel : ViewModel() {

    private val MAX_HOME_ROUTES = 10

    val _routes = MutableLiveData<Array<Route>>()
    val routes: LiveData<Array<Route>> = _routes

    suspend fun refreshRoutes() {

        val newRoutes = mutableListOf<Route>()

        newRoutes.addAll(ServerRouteManager.getRoutesByLocation(
            -4.332797f,
            1.953421f,
            30,
            MAX_HOME_ROUTES
        ))


        newRoutes.addAll(ServerRouteManager.getRandomRoutes(MAX_HOME_ROUTES - newRoutes.size))

        _routes.value = newRoutes.toTypedArray()
    }

    fun requestMoreRoutes() {
        // TODO
    }

}