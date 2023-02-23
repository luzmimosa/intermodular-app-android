package com.example.intermodular.ui.feature.register.ui

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.intermodular.model.Routes
import com.example.intermodular.ui.feature.register.domain.usecase.RegisterUseCase
import kotlinx.coroutines.launch

class RegisterViewModel: ViewModel() {

    private val MIN_PASSWORD_LENGTH = 6

    private val registerUseCase= RegisterUseCase()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _repeatedPassword = MutableLiveData<String>()
    val repeatedPassword: LiveData<String> = _repeatedPassword

    private val _user = MutableLiveData<String>()
    val user: LiveData<String> = _user

    private val _nombre = MutableLiveData<String>()
    val nombre: LiveData<String> = _nombre

    private val _emailAlertVisible = MutableLiveData<Boolean>()
    val emailAlertVisible: LiveData<Boolean> = _emailAlertVisible

    private val _passwordAlertVisible = MutableLiveData<Boolean>()
    val passwordAlertVisible: LiveData<Boolean> = _passwordAlertVisible

    private val _passwordConfirmAlertVisible = MutableLiveData<Boolean>()
    val passwordConfirmAlertVisible: LiveData<Boolean> = _passwordConfirmAlertVisible

    private val _userAlertVisible = MutableLiveData<Boolean>()
    val userAlertVisible: LiveData<Boolean> = _userAlertVisible

    private val _nombreAlertVisible = MutableLiveData<Boolean>()
    val nombreAlertVisible: LiveData<Boolean> = _nombreAlertVisible

    fun onRegisterChanged(email: String, password: String, user: String, nombre: String, repeatedPassword: String) {
        _email.value = email
        _password.value = password
        _user.value = user
        _nombre.value = nombre
        _repeatedPassword.value = repeatedPassword
    }

    private fun updateAlerts() {
        _emailAlertVisible.value =              !isValidEmail(email.value ?: "")
        _passwordAlertVisible.value =           !isValidPassword(password.value ?: "")
        _passwordConfirmAlertVisible.value =    !passwordsMatch(password.value ?: "", repeatedPassword.value ?: "")
        _userAlertVisible.value =               !isValidUser(user.value ?: "")
        _nombreAlertVisible.value =             !isValidName(nombre.value ?: "")
    }

    private fun isValidName(name: String): Boolean {
        return name.isNotEmpty()
    }

    private fun isValidUser(user: String): Boolean {
        return user.isNotEmpty()
    }

    private fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        return password.isNotEmpty() && password.length >= MIN_PASSWORD_LENGTH
    }

    private fun passwordsMatch(password: String, otherPassword: String): Boolean {
        return password == otherPassword
    }

    private fun isValidForm(): Boolean {
        return try {
            (
                isValidName(nombre.value!!)
                && isValidUser(user.value!!)
                && isValidEmail(email.value!!)
                && isValidPassword(password.value!!)
                && passwordsMatch(password.value!!, repeatedPassword.value!!)
            )
        } catch (e: Exception) {
            false
        }
    }


    fun onButtonRegisterPress(navigationController: NavHostController) {

        if (!isValidForm()) {
            updateAlerts()
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            val result = registerUseCase(email.value!!, password.value!!, nombre.value!!, user.value!!)

            if(result) {
                onRegisterOk(navigationController)
            } else {
                onRegisterError()
            }
            _isLoading.value = false
        }
    }

    fun onRegisterOk(navigationController: NavHostController) {
        navigationController.navigate(Routes.HomeScreen.route)
    }

    fun onRegisterError() {
        Log.i("WikiHonk", "Register failed")
    }

}