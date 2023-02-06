package com.example.intermodular.register.data.network

import com.example.intermodular.core.network.RetrofitHelper
import com.example.intermodular.register.data.dto.UserDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RegisterService {
    private val retrofit= RetrofitHelper.getRetrofit()

    suspend fun doRegister(name : String, user: String, email : String, password : String): Boolean {
        return withContext(Dispatchers.IO){
            val response = retrofit.create(RegisterClient::class.java).doRegister(UserDTO(name, user, email, password))

            return@withContext response.code() == 200
        }
    }
}