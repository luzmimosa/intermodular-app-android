package com.example.intermodular

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import com.example.intermodular.core.authentication.AuthenticationTokenManager
import com.example.intermodular.core.camera.CameraManager
import com.example.intermodular.core.location.WikihonkLocationManager
import com.example.intermodular.ui.component.global.ErrorPopup
import com.example.intermodular.ui.theme.IntermodularTheme

class MainActivity : ComponentActivity() {

    private var fineLocationPermissionGranted = false
    private var backgroundLocationPermissionGranted = false
    private var cameraPermissionGranted = false

    private val fineLocationPermissionRequester = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            fineLocationPermissionGranted = true

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                backgroundLocationPermissionGranted = true
            }
            showApplication()
        } else {
            showErrorAndClose(R.string.permission_location_denied)
        }
    }

    private val backgroundLocationPermissionRequester = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            backgroundLocationPermissionGranted = true
            showApplication()
        } else {
            showErrorAndClose(R.string.permission_backgroundlocation_denied)
        }
    }

    private val cameraPermissionRequester = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            cameraPermissionGranted = true
            showApplication()
        } else {
            showErrorAndClose(R.string.permission_camera_denied)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startCameraService()
        synchronizeToken()
        showApplication()
    }

    private fun synchronizeToken() {
        AuthenticationTokenManager.syncToken(this)
    }

    private fun checkPermissions() {
        this.fineLocationPermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            this.backgroundLocationPermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
        } else {
            this.backgroundLocationPermissionGranted = this.fineLocationPermissionGranted
        }
        this.cameraPermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermission() {
        fineLocationPermissionRequester.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private fun requestCameraPermission() {
        cameraPermissionRequester.launch(Manifest.permission.CAMERA)
    }

    private fun showApplication() {

        checkPermissions()

        if (!cameraPermissionGranted) {
            requestCameraPermission()
        } else {
            if (!fineLocationPermissionGranted) {
                requestLocationPermission()
            } else {
                if (!backgroundLocationPermissionGranted && Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    setContent {
                        ErrorPopup(
                            message = stringResource(id = R.string.permission_backgroundlocation_request),
                            buttonLabel = stringResource(id = R.string.global_popup_accept)
                        ) {
                            backgroundLocationPermissionRequester.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                        }
                    }
                } else {

//                    startCameraService()
                    startLocationService()

                    setContent {
                        IntermodularTheme {
                            Surface(
                                modifier = Modifier.fillMaxSize(),
                                color = MaterialTheme.colors.background
                            ) {
                                CustomNavigator(this)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun showErrorAndClose(errorSource: Int) {
        setContent {
            IntermodularTheme {
                ErrorPopup(
                    message = stringResource(id = errorSource),
                    buttonLabel = stringResource(id = R.string.global_popup_close)
                ) {
                    finish()
                }
            }
        }
    }

    private fun startLocationService() {
        WikihonkLocationManager.startListening(this)
    }

    private fun startCameraService() {
        CameraManager.registerRequester(this)
    }

}

