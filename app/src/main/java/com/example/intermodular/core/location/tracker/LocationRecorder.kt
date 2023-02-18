package com.example.intermodular.core.location.tracker

import com.example.intermodular.core.location.LocationMark
import com.example.intermodular.core.location.LocationSubscriber
import com.example.intermodular.core.location.WikihonkLocationManager
import java.util.*

class LocationRecorder : LocationSubscriber {

    private var state = LocationRecorderState.WAITING
    private val marks = mutableListOf<LocationMark>()


    fun startRecording() {
        this.state = LocationRecorderState.RECORDING
        WikihonkLocationManager.subscribe(this)

    }

    fun stopRecording() {
        this.state = LocationRecorderState.STOPPED
        WikihonkLocationManager.unsubscribe(this)
    }

    fun currentMarks(): List<LocationMark> {
        return this.marks.toList()
    }

    fun firstMark(): LocationMark? {
        return try {
            this.marks.first()
        } catch (e: NoSuchElementException) {
            null
        }
    }

    fun lastMark(): LocationMark? {
        return try {
            this.marks.last()
        } catch (e: NoSuchElementException) {
            null
        }
    }

    override fun onLocationUpdate(locationMark: LocationMark) {
        if (this.state == LocationRecorderState.RECORDING) {
            this.marks.add(locationMark)
        }
    }

}

enum class LocationRecorderState {
    WAITING,
    RECORDING,
    STOPPED
}