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
        val byteArrayOutputStream = ByteArrayOutputStream()
        image.asAndroidBitmap().compress(Bitmap.CompressFormat.PNG, 90, byteArrayOutputStream)

        val requestBody = RequestBody.create(
            MediaType.parse("image/png"),
            byteArrayOutputStream.toByteArray()
        )

        val response = RetrofitHelper.getRetrofit().create(ImageUploadClient::class.java).uploadImage(MultipartBody.Part.create(requestBody))

        if (response.isSuccessful) {
            return response.body()!!.id
        } else {
            //throw Exception("Error uploading image")
            return "I_DONT_WANNA_BE_A_LINK"
        }
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