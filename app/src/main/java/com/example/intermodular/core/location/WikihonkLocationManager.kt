package com.example.intermodular.core.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationListener
import android.location.LocationManager
import android.os.HandlerThread
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

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

        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)?.let {
            _userLocation.value = LocationMark(
                latitude = it.latitude,
                longitude = it.longitude
            )
        }

        locationListener().let {
            this.locationListener = it
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                100L,
                0.05f,
                it,
                locationHandlerThread.looper
            )
        }
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
}















