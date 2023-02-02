package com.example.intermodular.register.data.network

import com.example.intermodular.core.network.RetrofitHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RegisterService {
    private val retrofit= RetrofitHelper.getRetrofit()

    suspend fun doRegister(name : String, user: String, email : String, password : String): Boolean {
        return withContext(Dispatchers.IO){
            val response = retrofit.create(RegisterClient::class.java).doRegister(name, user, email, password)

            return@withContext response.body()?.success == true
        }
    }
}