package com.example.intermodular.login.data.network

import com.example.intermodular.core.network.RetrofitHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher

class LoginService {
    private val retrofit= RetrofitHelper.getRetrofit()

    suspend fun doLogin(user: String, password: String): Boolean{
        return withContext(Dispatchers.IO){
            val response= retrofit.create(LoginClient::class.java).doLogin()
            response.body()?.loginOk ?: false
        }
    }
}