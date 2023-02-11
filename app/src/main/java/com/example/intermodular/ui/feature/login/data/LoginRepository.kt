package com.example.intermodular.ui.feature.login.data

import com.example.intermodular.ui.feature.login.data.network.LoginService

class LoginRepository {
    private val api = LoginService()

    suspend fun doLogin(user: String, password: String, sendTokenTo: (token: String) -> Unit ) : Boolean {
        return api.doLogin(user, password, sendTokenTo)
    }
}