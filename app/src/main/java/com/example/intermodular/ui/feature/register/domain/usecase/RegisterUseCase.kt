package com.example.intermodular.ui.feature.register.domain.usecase

import com.example.intermodular.ui.feature.register.data.RegisterRepository

class RegisterUseCase {
    private val repository= RegisterRepository()

    suspend operator fun invoke(email: String, password: String, nombre: String, user: String): Boolean{
        return repository.doRegister(user, nombre, email, password)
    }
}