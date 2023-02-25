package com.example.intermodular.ui.feature.login.data.network

import android.util.Log
import com.example.intermodular.core.network.RetrofitHelper
import com.example.intermodular.ui.feature.login.data.dto.LoginDTO
import com.example.intermodular.ui.feature.login.data.network.response.LoginResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginService {
    private val retrofit= RetrofitHelper.getRetrofit()

    suspend fun doLogin(user: String, password: String, sendTokenTo: (token: String) -> Unit): LoginResult {
        return try {
            withContext(Dispatchers.IO) {
                val response = retrofit.create(LoginClient::class.java).doLogin(LoginDTO(user, password))

                val loginSuccessful = response.isSuccessful
                if (loginSuccessful) {
                    sendTokenTo(response.headers().get("Authorization") ?: "")
                }

                val loginResponseMessage = response.body()?.message ?: try {
                    Gson().fromJson(response.errorBody()?.string(), LoginResponse::class.java).message
                } catch (e: Exception) {
                    Log.e("LoginService", "Error parsing error body", e)
                    "PARSING_ERROR"
                }

                return@withContext loginResponseMessage.let {
                    when (it) {
                        "OK" -> LoginResult.SUCCESS
                        "INVALID_CREDENTIALS" -> LoginResult.WRONG_CREDENTIALS
                        "MISSING_PARAMS" -> LoginResult.WRONG_CREDENTIALS
                        else -> LoginResult.UNKNOWN_ERROR
                    }
                }
            }
        } catch (e: Exception) {
            return LoginResult.UNKNOWN_ERROR
        }
    }
}