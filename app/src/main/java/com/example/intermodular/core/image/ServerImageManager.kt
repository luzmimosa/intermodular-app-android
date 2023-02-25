package com.example.intermodular.core.image

import android.graphics.Bitmap
import android.graphics.BitmapFactory
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

    private val imageCache = mutableMapOf<String, ImageBitmap>()

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
            "I_DONT_WANNA_BE_A_LINK"
        }

        return imageID

    }

    suspend fun getImage(imageId: String, force: Boolean = false): ImageBitmap? {

        if (imageCache.containsKey(imageId) && !force) return imageCache[imageId]

        return try {
            val response = RetrofitHelper.getRetrofit().create(ImageDownloadClient::class.java).downloadImage(imageId)

            if (response.isSuccessful) {
                val bitmap = BitmapFactory.decodeStream(response.body()?.byteStream()).asImageBitmap()
                imageCache[imageId] = bitmap
                bitmap
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

}