package com.example.intermodular.register.domain.usecase

import com.example.intermodular.register.data.RegisterRepository

class RegisterUseCase {
    private val repository= RegisterRepository()

    suspend operator fun invoke(email: String, password: String, nombre: String, user: String): Boolean{
        return repository.doRegister(nombre, user, password, email)
    }
}