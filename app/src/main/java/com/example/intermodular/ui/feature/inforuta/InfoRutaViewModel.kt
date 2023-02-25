package com.example.intermodular.ui.feature.inforuta

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.intermodular.core.route.ServerRouteManager
import com.example.intermodular.core.route.model.Route
import com.example.intermodular.core.user.ServerUserManager
import com.example.intermodular.model.Routes
import kotlinx.coroutines.launch

class InfoRutaViewModel(
    private val routeID: String,
    private val navigationController: NavHostController
    ): ViewModel() {

    private val _route = MutableLiveData<Route>()
    val route: LiveData<Route> = _route

    private val _uploadCommentErrorPopupVisible = MutableLiveData<Boolean>()
    val uploadCommentErrorPopupVisible: LiveData<Boolean> = _uploadCommentErrorPopupVisible

    private val _isFavouriteRoute = MutableLiveData<Boolean>()
    val isFavouriteRoute: LiveData<Boolean> = _isFavouriteRoute

    private val _isToDoRoute = MutableLiveData<Boolean>()
    val isToDoRoute: LiveData<Boolean> = _isToDoRoute

    fun fetchRoute(force: Boolean = false) {

        if (_route.value != null && !force) return

        viewModelScope.launch {
            val route = ServerRouteManager.getRouteByID(routeID, true)
            _route.value = route
            _isFavouriteRoute.value = ServerUserManager.isFavouriteRoute(routeID)
            _isToDoRoute.value = ServerUserManager.isToDoRoute(routeID)
        }
    }

    fun handleMapPress() {
        navigationController.navigate(Routes.MapScreen.route(routeID))
    }

    fun handleLikePress() {
        viewModelScope.launch {
            if (ServerUserManager.isFavouriteRoute(routeID)) {
                ServerUserManager.removeFavouriteRoute(routeID)
                _isFavouriteRoute.value = false
            } else {
                ServerUserManager.addFavouriteRoute(routeID)
                _isFavouriteRoute.value = true
            }
        }
    }

    fun handleToDoPress() {
        viewModelScope.launch {
            if (ServerUserManager.isToDoRoute(routeID)) {
                ServerUserManager.removeToDoRoute(routeID)
                _isToDoRoute.value = false
            } else {
                ServerUserManager.addToDoRoute(routeID)
                _isToDoRoute.value = true
            }
        }
    }

    fun submitComment(comment: String) {
        viewModelScope.launch {
            if (ServerRouteManager.commentRoute(routeID, comment)) {
                fetchRoute(true)
            } else {
                _uploadCommentErrorPopupVisible.value = true
            }
        }
    }

    fun closeUploadCommentErrorPopup() {
        _uploadCommentErrorPopupVisible.value = false
    }

    fun updateRouteFromCache() {
        val cachedRoute = ServerRouteManager.getRouteCache().firstOrNull { it.uid == routeID } ?: return

        _route.value = cachedRoute
    }

}