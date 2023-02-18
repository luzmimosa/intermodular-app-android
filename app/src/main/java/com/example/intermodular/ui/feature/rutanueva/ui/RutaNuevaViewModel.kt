package com.example.intermodular.ui.feature.rutanueva.ui

import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.intermodular.core.location.tracker.LocationRecorder
import com.example.intermodular.core.route.ServerRouteManager
import com.example.intermodular.core.route.model.RouteDifficulty
import com.example.intermodular.core.route.model.RouteType
import com.example.intermodular.model.Routes
import com.example.intermodular.ui.feature.rutanueva.model.RawRoute
import com.example.intermodular.ui.feature.rutanueva.model.RawWaypoint
import kotlinx.coroutines.launch

class RutaNuevaViewModel(val navigationController: NavHostController) : ViewModel() {

    private val locationRecorder = LocationRecorder()
    private val waypoints = mutableListOf<RawWaypoint>()


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

    private val _waypointPopupVisible = MutableLiveData<Boolean>()
    val waypointPopupVisible: LiveData<Boolean> = _waypointPopupVisible

    private val _waypointCreatedPopupVisible = MutableLiveData<Boolean>()
    val waypointCreatedPopupVisible: LiveData<Boolean> = _waypointCreatedPopupVisible


    // Error visibilities
    private val _routeDescriptionErrorVisible = MutableLiveData<Boolean>()
    val routeDescriptionErrorVisible: LiveData<Boolean> = _routeDescriptionErrorVisible

    private val _routeTypesErrorVisible = MutableLiveData<Boolean>()
    val routeTypesErrorVisible: LiveData<Boolean> = _routeTypesErrorVisible

    private val _routeImageErrorVisible = MutableLiveData<Boolean>()
    val routeImageErrorVisible: LiveData<Boolean> = _routeImageErrorVisible

    private val _routeDifficultyErrorVisible = MutableLiveData<Boolean>()
    val routeDifficultyErrorVisible: LiveData<Boolean> = _routeDifficultyErrorVisible


    // Other
    private val _mainImageBitmap = MutableLiveData<ImageBitmap?>()
    val mainImageBitmap: LiveData<ImageBitmap?> = _mainImageBitmap

    private val _recordingState = MutableLiveData<RecordingState>()
    val recordingState: LiveData<RecordingState> = _recordingState

    private val _waypointName = MutableLiveData<String>()
    val waypointName: LiveData<String> = _waypointName

    private val _waypointDescription = MutableLiveData<String>()
    val waypointDescription: LiveData<String> = _waypointDescription

    private val _waypointImage = MutableLiveData<ImageBitmap>()
    val waypointImage: LiveData<ImageBitmap> = _waypointImage


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

    fun setRouteImageErrorVisible(visible: Boolean) {
        _routeImageErrorVisible.value = visible
    }

    fun setWaypointCreatedPopupVisible(visible: Boolean) {
        _waypointCreatedPopupVisible.value = visible
    }

    fun canAddWaypoint(): Boolean {
        return _recordingState.value == RecordingState.RECORDING && locationRecorder.lastMark() != null
    }

    fun setWaypointData(
        name: String,
        description: String,
        image: ImageBitmap?
    ) {
        _waypointName.value = name
        _waypointDescription.value = description
        _waypointImage.value = image
    }

    fun saveCurrentWaypoint() {

        waypoints.add(
            RawWaypoint(
                _waypointName.value ?: "",
                _waypointDescription.value ?: "",
                locationRecorder.lastMark()!!.latitude,
                locationRecorder.lastMark()!!.longitude,
                _waypointImage.value ?: ImageBitmap(1, 1)
            )
        )
        setWaypointCreatedPopupVisible(true)
        closeWaypointPopup()
    }

    fun closeWaypointPopup() {
        _waypointPopupVisible.value = false
        resetWaypointData()
    }

    fun startRecording() {
        _recordingState.value = RecordingState.RECORDING
        locationRecorder.startRecording()
    }

    fun stopRecording() {
        locationRecorder.stopRecording()

        if (locationRecorder.currentMarks().isEmpty()) {
            _recordingState.value = RecordingState.READY
        } else {
            _recordingState.value = RecordingState.RECORDED
        }
    }

    fun addWaypointRequest() {
        resetWaypointData()
        _waypointPopupVisible.value = true
    }

    private fun resetWaypointData() {
        _waypointName.value = ""
        _waypointDescription.value = ""
        _waypointImage.value = null
    }

    fun requestUpload() {
        if (!checkFields()) {
            showErrors()
            return
        }

        val route = RawRoute(
            _routeName.value!!,
            _routeDescription.value!!,
            _routeDifficulty.value!!,
            _routeTypes.value!!,
            _mainImageBitmap.value!!,
            locationRecorder.currentMarks(),
            waypoints
        )

        viewModelScope.launch {
            val uploadableRoute = route.asUploadableRoute()

            val result = ServerRouteManager.uploadRoute(uploadableRoute)

            if (result) {
                Log.i("RouteUpload", "Route uploaded successfully")
                navigationController.navigate(Routes.HomeScreen.route)
            } else {
                Log.i("RouteUpload", "Route upload failed")
                navigationController.navigate(Routes.UserInfoScreen.route)
            }
        }
    }

    private fun checkFields(): Boolean =
                checkName()
                && checkDescription()
                && checkDifficulty()
                && checkTypes()
                && checkImage()
                && checkRecording()

    private fun checkName(): Boolean        = _routeName.value?.isNotEmpty() ?: false

    private fun checkDescription(): Boolean = _routeDescription.value?.isNotEmpty() ?: false

    private fun checkDifficulty(): Boolean  = _routeDifficulty.value != null

    private fun checkTypes(): Boolean       = _routeTypes.value?.isNotEmpty() ?: false

    private fun checkImage(): Boolean       = _mainImageBitmap.value != null

    private fun checkRecording(): Boolean   = _recordingState.value == RecordingState.RECORDED && locationRecorder.currentMarks().isNotEmpty()

    private fun showErrors() {
        if (!checkName()) {
            _routeNamePopupVisible.value = true
            return
        }

        _routeDescriptionErrorVisible.value     = !checkDescription()
        _routeDifficultyErrorVisible.value      = !checkDifficulty()
        _routeTypesErrorVisible.value           = !checkTypes()
        _routeImageErrorVisible.value           = !checkImage()

    }

}

enum class RecordingState {
    READY,
    RECORDING,
    RECORDED
}