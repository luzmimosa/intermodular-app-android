package com.example.intermodular.map.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.intermodular.core.route.ServerRouteManager
import com.example.intermodular.core.route.model.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(): ViewModel() {

    private val _route = MutableLiveData<Route?>()
    val route: LiveData<Route?> = _route


    init {
        viewModelScope.launch {

            Log.i("WikiHonk", "Fetching route")
            val fetchedRoute = ServerRouteManager.getRouteByID("fdbc284c99cbfb59b5e66531689f362b76cf8db325271b7cd183e0a9c2c0c135")
            Log.i("WikiHonk", "Route fetched: ${fetchedRoute.toString()}")
            _route.value = fetchedRoute
        }
    }
}