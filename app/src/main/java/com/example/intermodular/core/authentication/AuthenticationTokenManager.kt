package com.example.intermodular.core.authentication

import android.content.Context

object AuthenticationTokenManager {

    private val TOKEN_KEY = "login_token"

    private var _token: String? = null
    val token get() = _token

    fun hasToken(): Boolean {
        return token != null
    }

    fun syncToken(context: Context) {
        val sharedPreferences = context.getSharedPreferences(TOKEN_KEY, Context.MODE_PRIVATE)
        _token = sharedPreferences.getString(TOKEN_KEY, null)
    }

    fun saveToken(context: Context, token: String?) {
        val sharedPreferences = context.getSharedPreferences(TOKEN_KEY, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString(TOKEN_KEY, token)
        editor.apply()
    }

    fun verifyToken(context: Context): Boolean {
        if (!hasToken()) return false

        return true // TODO: Verify token
    }

}