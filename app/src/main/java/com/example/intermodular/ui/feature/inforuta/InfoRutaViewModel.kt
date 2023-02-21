package com.example.intermodular.ui.feature.inforuta

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.intermodular.core.route.ServerRouteManager
import com.example.intermodular.core.route.model.Route

class InfoRutaViewModel(val routeID: String): ViewModel() {

    private val _route = MutableLiveData<Route>()
    val route: LiveData<Route> = _route

    private val _isRouteResolved = MutableLiveData<Boolean>()
    val isRouteResolved: LiveData<Boolean> = _isRouteResolved

    private var resolvedRoute: Route? = null

    suspend fun fetchRoute() {
        val fetchedRoute = ServerRouteManager.getRouteByID(routeID)

        if (fetchedRoute != null) {
            resolvedRoute = fetchedRoute
        } else {
            Log.i("InfoRutaViewModel", "Error fetching route")
        }
    }

    fun resolveRoute() {
        if (resolvedRoute != null) {
            _route.value = resolvedRoute
            _isRouteResolved.value = true
        } else {
            Log.i("InfoRutaViewModel", "Error resolving route")
        }
    }

}