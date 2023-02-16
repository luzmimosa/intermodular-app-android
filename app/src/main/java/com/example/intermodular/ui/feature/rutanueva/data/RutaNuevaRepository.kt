package com.example.intermodular.ui.feature.rutanueva.data

import com.example.intermodular.ui.feature.register.data.network.RegisterService
import com.example.intermodular.ui.feature.rutanueva.data.network.RutaNuevaService

class RutaNuevaRepository {
    private val api= RutaNuevaService()

    suspend fun doCrear(nombre: String, descripcion: String, dificultad: String): Boolean {
        return api.doCrear(nombre, descripcion, dificultad)
    }
}