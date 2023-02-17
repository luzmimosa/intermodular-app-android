package com.example.intermodular.ui.feature.rutanueva.ui

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.intermodular.core.route.model.RouteDifficulty
import com.example.intermodular.core.route.model.RouteType

class RutaNuevaViewModel: ViewModel() {

    // Fields
    private val _routeName = MutableLiveData<String>()
    val routeName: LiveData<String> = _routeName

    private val _routeDescription = MutableLiveData<String>()
    val routeDescription: LiveData<String> = _routeDescription

    private val _routeDifficulty = MutableLiveData<RouteDifficulty>()
    val routeDifficulty: LiveData<RouteDifficulty> = _routeDifficulty

    private val _routeTypes = MutableLiveData<List<RouteType>>()
    val routeTypes: LiveData<List<RouteType>> = _routeTypes

    // Popup visibilities

    private val _routeNamePopupVisible = MutableLiveData<Boolean>()
    val routeNamePopupVisible: LiveData<Boolean> = _routeNamePopupVisible

    private val _routeDifficultyPopupVisible = MutableLiveData<Boolean>()
    val routeDifficultyPopupVisible: LiveData<Boolean> = _routeDifficultyPopupVisible

    // Error messages
    private val _routeDescriptionErrorMessage = MutableLiveData<Int>()
    val routeDescriptionErrorMessage: LiveData<Int> = _routeDescriptionErrorMessage

    // Error visibilities
    private val _routeDescriptionErrorVisible = MutableLiveData<Boolean>()
    val routeDescriptionErrorVisible: LiveData<Boolean> = _routeDescriptionErrorVisible

    // Other
    private val _mainImageBitmap = MutableLiveData<ImageBitmap?>()
    val mainImageBitmap: LiveData<ImageBitmap?> = _mainImageBitmap

    fun setName(name: String) {
        _routeName.value = name
    }

    fun setDescription(description: String) {
        _routeDescription.value = description
    }

    fun setDifficulty(difficulty: RouteDifficulty) {
        _routeDifficulty.value = difficulty
    }

    fun setMainImage(imageBitmap: ImageBitmap?) {
        _mainImageBitmap.value = imageBitmap
    }

    fun setRouteNamePopupVisible(visible: Boolean) {
        _routeNamePopupVisible.value = visible
    }

    fun addRouteType(type: RouteType) {
        _routeTypes.value = _routeTypes.value?.plus(type) ?: listOf(type)
    }

    fun removeRouteType(type: RouteType) {
        _routeTypes.value = _routeTypes.value?.minus(type) ?: listOf()
    }

    fun setRouteDifficultyPopupVisible(visible: Boolean) {
        _routeDifficultyPopupVisible.value = visible
    }
}