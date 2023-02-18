package com.example.intermodular.core.image

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.example.intermodular.core.image.client.ImageDownloadClient
import com.example.intermodular.core.image.client.ImageUploadClient
import com.example.intermodular.core.network.RetrofitHelper
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream

object ServerImageManager {

    suspend fun uploadImage(image: ImageBitmap): String {
        val stream = ByteArrayOutputStream()
        image.asAndroidBitmap().compress(Bitmap.CompressFormat.PNG, 100, stream)
        val byteArray = stream.toByteArray()

        val requestFile = RequestBody.create(MediaType.parse("image/png"), byteArray)
        val imagePart = MultipartBody.Part.createFormData("image", "image.png", requestFile)

        val response = RetrofitHelper.getRetrofit().create(ImageUploadClient::class.java).uploadImage(imagePart)

        val imageID = if (response.isSuccessful) {
            response.body()!!.id
        } else {
            Log.i("ServerImageManager (uploadImage)", "Image upload failed, response: ${response.code()}")
            "I_DONT_WANNA_BE_A_LINK"
        }

        Log.i("ServerImageManager (uploadImage)", "Image uploaded, ID: $imageID")

        return imageID

    }

    suspend fun getImage(imageId: String): ImageBitmap? {
        try {
            val response = RetrofitHelper.getRetrofit().create(ImageDownloadClient::class.java).downloadImage(imageId)

            return if (response.isSuccessful) {
                BitmapFactory.decodeStream(response.body()?.byteStream()).asImageBitmap()
            } else {
                null
            }
        } catch (e: Exception) {
            Log.i("ServerImageManager", "getImage ERROR: $e")
            return null
        }
    }

}