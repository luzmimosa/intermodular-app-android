package com.example.intermodular.ui.feature.register.data.network

import android.util.Log
import com.example.intermodular.core.network.RetrofitHelper
import com.example.intermodular.ui.feature.register.data.dto.UserDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RegisterService {
    private val retrofit= RetrofitHelper.getRetrofit()

    suspend fun doRegister(name : String, user: String, email : String, password : String): Boolean {
        return try {
            withContext(Dispatchers.IO){
                val response = retrofit.create(RegisterClient::class.java).doRegister(UserDTO(name, user, email, password))

                return@withContext response.code() == 200
            }
        } catch (e: Exception) {
            Log.e("RegisterService", "Error: ${e.message}")
            false
        }
    }
}