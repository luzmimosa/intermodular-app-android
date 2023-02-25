package com.example.intermodular.ui.feature.userinfo.ui

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.intermodular.core.authentication.AuthenticationTokenManager
import com.example.intermodular.core.camera.CameraManager
import com.example.intermodular.core.user.ServerUserManager
import com.example.intermodular.core.user.model.User
import kotlinx.coroutines.launch

class UserInfoViewModel(val context: Context): ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _displayNamePopupVisible = MutableLiveData<Boolean>()
    val displayNamePopupVisible: LiveData<Boolean> = _displayNamePopupVisible

    private val _bioPopupVisible = MutableLiveData<Boolean>()
    val bioPopupVisible: LiveData<Boolean> = _bioPopupVisible

    fun loadUser(): User? {
        val cachedUser = ServerUserManager.getSelfUserOrNull()
        _user.value = cachedUser
        return cachedUser
    }

    fun logOut() {
        AuthenticationTokenManager.saveToken(context, null)
    }

    fun handlePictureRequest() {
        CameraManager.takePicture { image ->

            if (image == null) return@takePicture
            viewModelScope.launch {
                if (ServerUserManager.setProfilePicture(image)) {
                    _user.value = ServerUserManager.getSelfUserOrNull()
                }
            }
        }
    }

    fun handleNameRequest(newName: String) {
        viewModelScope.launch {
            if (ServerUserManager.setDisplayName(newName)) {
                _user.value = ServerUserManager.getSelfUserOrNull()
            }
        }
    }

    fun handleBioRequest(newBio: String) {
        viewModelScope.launch {
            if (ServerUserManager.setBio(newBio)) {
                _user.value = ServerUserManager.getSelfUserOrNull()
            }
        }
    }

    fun setDisplayNamePopupVisible(visible: Boolean) {
        _displayNamePopupVisible.value = visible
    }

    fun setBioPopupVisible(visible: Boolean) {
        _bioPopupVisible.value = visible
    }

}