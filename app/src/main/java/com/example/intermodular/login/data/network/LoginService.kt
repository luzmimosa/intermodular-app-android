package com.example.intermodular.login.data.network

import com.example.intermodular.core.network.RetrofitHelper
import com.example.intermodular.login.data.dto.LoginDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginService {
    private val retrofit= RetrofitHelper.getRetrofit()

    suspend fun doLogin(user: String, password: String, sendTokenTo: (token: String) -> Unit): Boolean{
        return withContext(Dispatchers.IO){
            val response= retrofit.create(LoginClient::class.java).doLogin(LoginDTO(user, password))

            val loginSuccessful = response.isSuccessful

            if (loginSuccessful) {
                sendTokenTo(response.headers().get("Authorization") ?: "")
            }

            return@withContext loginSuccessful
        }
    }
}