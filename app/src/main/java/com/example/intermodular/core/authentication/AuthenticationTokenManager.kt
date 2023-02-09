package com.example.intermodular.core.authentication

object AuthenticationTokenManager {

    var token: String? = null

    fun hasToken(): Boolean {
        return token == null
    }

}