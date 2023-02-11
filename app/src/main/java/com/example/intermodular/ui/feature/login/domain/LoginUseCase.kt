package com.example.intermodular.ui.feature.login.domain

import android.util.Log
import com.example.intermodular.core.authentication.AuthenticationTokenManager
import com.example.intermodular.ui.feature.login.data.LoginRepository
import com.example.intermodular.ui.feature.login.data.network.LoginResult

class LoginUseCase {
    private val repository= LoginRepository()

    suspend operator fun invoke(user: String, password: String): LoginResult {
        return repository.doLogin(user, password) {
            token -> AuthenticationTokenManager.token = token
            Log.i("WikiHonk debug", "Token: $token")
        }
    }
}