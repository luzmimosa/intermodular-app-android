package com.example.intermodular.register.data

import com.example.intermodular.register.data.network.RegisterService

class RegisterRepository {
    private val api= RegisterService()

    suspend fun doRegister(user: String, nombre: String, email: String, password: String): Boolean {
        return api.doRegister(nombre, user, email, password)
    }
}