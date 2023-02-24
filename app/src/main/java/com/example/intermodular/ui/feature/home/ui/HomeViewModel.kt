package com.example.intermodular.ui.feature.home.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.intermodular.core.location.WikihonkLocationManager
import com.example.intermodular.core.route.ServerRouteManager
import com.example.intermodular.core.route.model.Route
import com.example.intermodular.core.user.ServerUserManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel(
    routeClassification: RouteClassification,
) : ViewModel() {

    private var _routeClassification = routeClassification
    val routeClassification: RouteClassification
        get() = _routeClassification

    val routes = MutableLiveData<Array<Route>>()

    val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    var loadingScore = 1

    fun requestRoutes() {
        viewModelScope.launch {
            cacheAllRoutes()
            loadingScore -= 1

            while (loadingScore > 0) {
                delay(500)
            }

            filterRoutesFromCache()

        }
    }

    fun filterRoutesFromCache() {
        routes.postValue(
            ServerRouteManager
                .getRouteCache()
                .filter { routeClassification.filter(it) }
                .toTypedArray()
        )
    }

    private suspend fun cacheAllRoutes() {
        loadCreatedRoutes()
        loadFavouriteRoutes()
        loadToDoRoutes()
        loadNearbyRoutes()
        loadRandomRoutes()
    }

    fun updateLoading() {
        _isLoading.postValue(loadingScore > 0)
    }

    private suspend fun loadCreatedRoutes() {
        val createdRoutesList = ServerRouteManager.getCreatedRoutesList(ServerUserManager.getSelfUser()?.username ?: "")

        for (routeUID in createdRoutesList) {
            loadingScore += 1
            viewModelScope.launch {
                ServerRouteManager.getRouteByID(routeUID)
                loadingScore -= 1
            }
        }
    }

    private suspend fun loadFavouriteRoutes() {
        val favouriteRoutesList = ServerUserManager.getSelfUser()?.featuredRoutes ?: listOf()
        for (routeUID in favouriteRoutesList) {
            loadingScore += 1
            viewModelScope.launch {
                ServerRouteManager.getRouteByID(routeUID)
                loadingScore -= 1
            }
        }
    }

    private suspend fun loadToDoRoutes() {
        val todoRoutesList = ServerUserManager.getSelfUser()?.toDoRoutes ?: listOf()
        for (routeUID in todoRoutesList) {
            loadingScore += 1
            viewModelScope.launch {
                ServerRouteManager.getRouteByID(routeUID)
                loadingScore -= 1
            }
        }
    }

    private suspend fun loadNearbyRoutes() {
        val nearbyRoutesList = ServerRouteManager.getLocatedRoutesList(
            WikihonkLocationManager.userLocation.value?.latitude ?: 0.0,
            WikihonkLocationManager.userLocation.value?.longitude ?: 0.0
        )
        for (routeUID in nearbyRoutesList) {
            loadingScore += 1
            viewModelScope.launch {
                ServerRouteManager.getRouteByID(routeUID)
                loadingScore -= 1
            }
        }
    }

    private suspend fun loadRandomRoutes() {
        val randomRoutesList = ServerRouteManager.getRandomRoutesList()
        for (routeUID in randomRoutesList) {
            loadingScore += 1
            viewModelScope.launch {
                ServerRouteManager.getRouteByID(routeUID)
            }
            loadingScore -= 1
        }
    }

}

enum class RouteClassification(
    val value: String,
    val filter: (route: Route) -> Boolean = { true }
) {
    ALL(
        "all"
    ),
    FAVOURITE(
        "favourite",
        { ServerUserManager.isFavouriteRoute(it.uid) }
    ),
    TODO(
        "todo",
        { ServerUserManager.isToDoRoute(it.uid) }
    ),
    CREATED(
        "created",
        {  (ServerUserManager.getSelfUserOrNull()?.username ?: "").equals(it.creator) }
    );

}