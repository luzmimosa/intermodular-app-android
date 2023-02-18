package com.example.intermodular.core.location

interface LocationSubscriber {

    fun onLocationUpdate(locationMark: LocationMark)

}