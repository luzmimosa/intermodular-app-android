package com.example.intermodular.login.data.database

interface IUserPreferences {
    suspend fun addToken(key: String, value: String)
    suspend fun getToken(key: String) : String
}