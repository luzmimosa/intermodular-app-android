package com.example.intermodular

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.intermodular.ui.component.global.ErrorPopup
import com.example.intermodular.ui.theme.IntermodularTheme

class MainActivity : ComponentActivity() {
//
//    private var locationPermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//    private var backgroundLocationPermissionGranted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//        ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED
//    } else {
//        locationPermissionGranted
//    }
//
//    private val locationPermission = registerForActivityResult(
//        ActivityResultContracts.RequestPermission()
//    ) { isGranted: Boolean ->
//        if (isGranted) {
//            locationPermissionGranted = true
//            // check current api
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    setContent {
//                        IntermodularTheme {
//                            ErrorPopup(
//                                message = stringResource(id = R.string.request_background_location_permission_message),
//                                buttonLabel = stringResource(id = R.string.global_popup_accept)
//                            ) {
//                                backgroundLocationPermission.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
//                            }
//                        }
//                    }
//                }
//            } else {
//                showApplication()
//            }
//        } else {
//            setContent {
//                IntermodularTheme {
//                    ErrorPopup(
//                        message = stringResource(id = R.string.denied_location_permission_message),
//                        buttonLabel = stringResource(id = R.string.global_popup_close)
//                    ) {
//                        finish()
//                    }
//                }
//            }
//        }
//    }
//
//    private val backgroundLocationPermission = registerForActivityResult(
//        ActivityResultContracts.RequestPermission()
//    ) { isGranted: Boolean ->
//        if (isGranted) {
//            backgroundLocationPermissionGranted = true
//            showApplication()
//        } else {
//            setContent {
//                IntermodularTheme {
//                    ErrorPopup(
//                        message = stringResource(id = R.string.denied_background_location_permission_message),
//                        buttonLabel = stringResource(id = R.string.global_popup_close)
//                    ) {
//                        finish()
//                    }
//                }
//            }
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        showApplication();
        if (1 == 1) return

//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            locationPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
//        }
    }

    private fun showApplication() {
        setContent {
            IntermodularTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    CustomNavigator()
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    IntermodularTheme {

    }
}

