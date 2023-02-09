package com.example.intermodular.login.data

import com.example.intermodular.login.data.network.LoginService

class LoginRepository {
    private val api = LoginService()

    suspend fun doLogin(user: String, password: String, sendTokenTo: (token: String) -> Unit ) : Boolean {
        return api.doLogin(user, password, sendTokenTo)
    }
}