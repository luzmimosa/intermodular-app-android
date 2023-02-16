package com.example.intermodular.core.location

import java.time.LocalDateTime

data class LocationMark(
    val latitude: Double,
    val longitude: Double,
    val dateTime: LocalDateTime = LocalDateTime.now()
)
