package com.example.intermodular.core.camera

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.example.intermodular.MainActivity

object CameraManager {

    private lateinit var imageRequester: ActivityResultLauncher<Void?>
    private var imageHandler: (ImageBitmap?) -> Unit = {}

    fun registerRequester(activity: MainActivity) {
        imageRequester = activity.registerForActivityResult(
                ActivityResultContracts.TakePicturePreview(),
        ) { image ->
            imageHandler(image?.asImageBitmap())
        }
    }

    fun takePicture(handler: (ImageBitmap?) -> Unit) {
        imageHandler = handler
        imageRequester.launch(null)
    }

}