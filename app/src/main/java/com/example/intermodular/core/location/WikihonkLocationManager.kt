package com.example.intermodular.core.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.Granularity
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task

class WikihonkLocationManager {

    private val _userLocation = MutableLiveData<LocationMark>()
    val userLocation: LiveData<LocationMark> = _userLocation

    private var listener: Task<Void>? = null

    fun startListening(context: Context) {
        if (listener != null) {
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

        this.listener = LocationServices
            .getFusedLocationProviderClient(context)
            .requestLocationUpdates(
                LocationRequest.Builder(2500)
                    .setMaxUpdates(Integer.MAX_VALUE)
                    .setMinUpdateDistanceMeters(4f)
                    .setGranularity(Granularity.GRANULARITY_FINE)
                    .build(),
                {

                },
                { location ->
                    _userLocation.value = LocationMark(
                        latitude = location.latitude,
                        longitude = location.longitude
                    )
                }
        )
    }
}















