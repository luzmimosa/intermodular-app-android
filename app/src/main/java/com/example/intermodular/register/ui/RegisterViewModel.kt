package com.example.intermodular.register.ui

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.intermodular.model.Routes
import com.example.intermodular.register.domain.usecase.RegisterUseCase
import kotlinx.coroutines.launch

class RegisterViewModel {
    private val registerUseCase= RegisterUseCase()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _user = MutableLiveData<String>()
    val user: LiveData<String> = _user

    private val _nombre = MutableLiveData<String>()
    val nombre: LiveData<String> = _nombre

    private val _isButtonRegisterEnable = MutableLiveData<Boolean>()
    val isButtonRegisterEnable: LiveData<Boolean> = _isButtonRegisterEnable

    fun onRegisterChanged(email: String, password: String) {
        _email.value = email
        _password.value = password
        _user.value = user
        _nombre.value = nombre
        _isButtonRegisterEnable.value = enableRegister(email, password, nombre, user)
    }

    private fun enableRegister(email: String, password: String, nombre: String, user: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(email).matches()
                && password.length > 5

    fun onButtonRegisterPress(navigationController: NavHostController) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = registerUseCase(email.value!!, password.value!!, user.value!!, nombre.value!!)

            if(result) {
                navigationController.navigate(Routes.HomeScreen.route)
                Log.i("WikiHonk", "Login OK")
            }
            _isLoading.value = false
        }
    }

}