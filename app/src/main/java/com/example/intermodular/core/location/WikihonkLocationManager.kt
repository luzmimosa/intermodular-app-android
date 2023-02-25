package com.example.intermodular.core.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.HandlerThread
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices

object WikihonkLocationManager {

    private val _userLocation = MutableLiveData<LocationMark>()
    val userLocation: LiveData<LocationMark> = _userLocation

    private var locationListener: LocationListener? = null
    private val locationHandlerThread = HandlerThread("Location listener thread")

    private val subscribers = mutableListOf<LocationSubscriber>()

    fun startListening(context: Context) {
        if (locationListener != null) {
            return
        }

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            throw Exception("Location permission not granted")
        }

        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    _userLocation.value = LocationMark(
                        latitude = location.latitude,
                        longitude = location.longitude
                    )
                    notifySubscribers(_userLocation.value!!)
                }
            }

        fusedLocationClient.requestLocationUpdates(
            LocationRequest.Builder(
                100
            )
                .setMinUpdateDistanceMeters(0.05f)
                .setMaxUpdates(Int.MAX_VALUE)
                .setIntervalMillis(100)
                .build(),
            locationListener(),
            locationHandlerThread.looper
        )

    }

    private fun notifySubscribers(locationMark: LocationMark) {
        subscribers.forEach { it.onLocationUpdate(locationMark) }
    }

    fun subscribe(subscriber: LocationSubscriber) {
        subscribers.add(subscriber)
    }

    fun unsubscribe(subscriber: LocationSubscriber) {
        subscribers.remove(subscriber)
    }

    private fun locationListener(): LocationListener =
        LocationListener { newLocation ->
            _userLocation.value = LocationMark(
                latitude = newLocation.latitude,
                longitude = newLocation.longitude
            )
            notifySubscribers(_userLocation.value!!)
        }

    fun currentLocation(): LocationMark? = _userLocation.value
}















