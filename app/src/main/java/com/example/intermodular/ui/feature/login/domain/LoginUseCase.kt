package com.example.intermodular.ui.feature.login.domain

import android.content.Context
import com.example.intermodular.core.authentication.AuthenticationTokenManager
import com.example.intermodular.ui.feature.login.data.LoginRepository
import com.example.intermodular.ui.feature.login.data.network.LoginResult

class LoginUseCase(val context: Context) {
    private val repository = LoginRepository()

    suspend operator fun invoke(user: String, password: String): LoginResult {
        return repository.doLogin(user, password) {
            token -> AuthenticationTokenManager.saveToken(context, token)
        }
    }
}