package com.example.intermodular.ui.feature.rutanueva.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.intermodular.model.Routes
import com.example.intermodular.ui.feature.rutanueva.domain.usecase.RutaNuevaUseCase
import kotlinx.coroutines.launch

class RutaNuevaViewModel: ViewModel() {

    private val rutaNuevaUseCase= RutaNuevaUseCase()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _nombre= MutableLiveData<String>()
    val nombre: LiveData<String> = _nombre

    private val _descripcion= MutableLiveData<String>()
    val descripcion: LiveData<String> = _descripcion

    private val _dificultad= MutableLiveData<String>()
    val dificultad: LiveData<String> = _dificultad

    private val _nombreAlertVisible= MutableLiveData<Boolean>()
    val nombreAlertVisible: LiveData<Boolean> = _nombreAlertVisible

    private val _descripcionAlertVisible= MutableLiveData<Boolean>()
    val descripcionAlertVisible: LiveData<Boolean> = _descripcionAlertVisible

    private val _dificultadAlertVisible= MutableLiveData<Boolean>()
    val dificultadAlertVisible: LiveData<Boolean> = _dificultadAlertVisible

    /*private val _tipoAlertVisible= MutableLiveData<Boolean>()
    val tipoAlertVisible: LiveData<Boolean> = _tipoAlertVisible*/

    fun onRegisterChanged(nombre: String, descripcion: String, dificultad: String) {
        _nombre.value = nombre
        _descripcion.value = descripcion
        _dificultad.value = dificultad
    }

    private fun updateAlerts() {
        _nombreAlertVisible.value =              !isValidNombre(nombre.value ?: "")
        _descripcionAlertVisible.value =           !isValidDescripcion(descripcion.value ?: "")
        _dificultadAlertVisible.value =               !isValidDificultad(dificultad.value ?: "")
    }

    private fun isValidNombre(nombre: String): Boolean {
        return nombre.isNotEmpty()
    }

    private fun isValidDescripcion(descripcion: String): Boolean {
        return descripcion.isNotEmpty()
    }

    private fun isValidDificultad(dificultad: String): Boolean {
        return dificultad.isNotEmpty()
    }

    private fun isValidRuta(): Boolean {
        return try {
            (
                    isValidNombre(nombre.value!!)
                            && isValidDescripcion(descripcion.value!!)
                            && isValidDificultad(dificultad.value!!)
                    )
        } catch (e: Exception) {
            false
        }
    }

    fun onButtonCrearRutaPress(navigationController: NavHostController) {

        if (!isValidRuta()) {
            updateAlerts()
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            val result = rutaNuevaUseCase(nombre.value!!, descripcion.value!!, dificultad.value!!)

            if(result) {
                onCrearRutaOk(navigationController)
            } else {
                onCrearRutaError()
            }
            _isLoading.value = false
        }
    }

    fun onCrearRutaOk(navigationController: NavHostController) {
        Log.i("WikiHonk", "Ruta creada")

        navigationController.navigate(Routes.HomeScreen.route)
    }

    fun onCrearRutaError() {
        Log.i("WikiHonk", "Ruta error")
    }
}