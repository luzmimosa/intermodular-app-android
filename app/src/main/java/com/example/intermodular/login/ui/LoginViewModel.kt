package com.example.intermodular.login.ui

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.intermodular.login.domain.LoginUseCase
import com.example.intermodular.model.Routes
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel(){
    private val loginUseCase= LoginUseCase()

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _isButtonLoginEnable = MutableLiveData<Boolean>()
    val isButtonLoginEnable: LiveData<Boolean> = _isButtonLoginEnable

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun onLoginChanged(email: String, password: String) {
        _email.value = email
        _password.value = password
        _isButtonLoginEnable.value = enableLogin(email, password)
    }

    private fun enableLogin(email: String, password: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(email).matches()
                && password.length > 5

    fun onButtonLoginPress(navigationController: NavHostController) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = loginUseCase(email.value!!, password.value!!)

            if(result) {
                navigationController.navigate(Routes.HomeScreen.route)
                Log.i("WikiHonk", "Login OK")
            }
            _isLoading.value = false
        }
    }

}