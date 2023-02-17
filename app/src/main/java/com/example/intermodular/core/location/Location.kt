package com.example.intermodular.core.location

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class LocationMark(
    val latitude: Double,
    val longitude: Double,
    val dateTime: LocalDateTime = LocalDateTime.now()
) {
    override fun toString(): String {
        return "LocationMark(latitude=$latitude, longitude=$longitude, dateTime=${dateTime.format(DateTimeFormatter.ISO_DATE_TIME)})"
    }
}
