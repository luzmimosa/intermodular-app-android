package com.example.intermodular.ui.feature.login.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.intermodular.ui.feature.login.domain.LoginUseCase
import com.example.intermodular.model.Routes
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel(){
    private val loginUseCase= LoginUseCase()

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _isButtonLoginEnable = MutableLiveData<Boolean>()
    val isButtonLoginEnable: LiveData<Boolean> = _isButtonLoginEnable

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun onLoginChanged(username: String, password: String) {
        _username.value = username
        _password.value = password
        _isButtonLoginEnable.value = enableLogin(username, password)
    }

    private fun enableLogin(email: String, password: String): Boolean = email.isNotEmpty() && password.isNotEmpty()

    fun onButtonLoginPress(navigationController: NavHostController) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = loginUseCase(username.value!!, password.value!!)

            if(result) {
                navigationController.navigate(Routes.HomeScreen.route)
                Log.i("WikiHonk", "Login OK")
            } else {
                Log.i("WikiHonk", "Login failed")
            }
            _isLoading.value = false
        }
    }

}