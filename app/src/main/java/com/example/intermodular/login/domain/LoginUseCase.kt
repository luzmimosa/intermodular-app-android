package com.example.intermodular.login.domain

import com.example.intermodular.login.data.LoginRepository

class LoginUseCase {
    private val repository= LoginRepository()

    suspend operator fun invoke(user: String, password: String): Boolean{
        return repository.doLogin(user, password)
    }
}