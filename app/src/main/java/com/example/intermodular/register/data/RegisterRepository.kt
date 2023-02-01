package com.example.intermodular.register.data

import com.example.intermodular.register.data.network.RegisterService

class RegisterRepository {
    private val api= RegisterService()

    suspend fun doRegistro(user: String, nombre: String, email: String, password: String){
        return api.doRegistro(user, nombre, email, password)
    }
}