package com.example.intermodular.ui.feature.rutanueva.domain.usecase

import com.example.intermodular.ui.feature.rutanueva.data.RutaNuevaRepository

class RutaNuevaUseCase {
    private val repository= RutaNuevaRepository()

    suspend operator fun invoke(nombre: String, descripcion: String, dificultad: String): Boolean{
        //return repository.doCreate(nombre, descripcion, dificultad)
        return false
    }
}