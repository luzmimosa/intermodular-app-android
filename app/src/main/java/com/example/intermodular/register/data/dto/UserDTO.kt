package com.example.intermodular.register.data.dto

data class UserDTO(
    val displayName: String,
    val username: String,
    val email: String,
    val password: String,
    val biography: String = ":("
)