package com.example.intermodular.ui.feature.rutanueva.ui

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RutaNuevaViewModel: ViewModel() {

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

    private val _mainImageBitmap = MutableLiveData<ImageBitmap?>()
    val mainImageBitmap: LiveData<ImageBitmap?> = _mainImageBitmap

    fun setMainImage(imageBitmap: ImageBitmap?) {
        _mainImageBitmap.value = imageBitmap
    }
}