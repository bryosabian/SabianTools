package com.sabiantools.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore

@Throws(Exception::class)
fun Uri.toBitmap(context: Context): Bitmap {
    val contentResolver = context.contentResolver
    val bitmap = if (Build.VERSION.SDK_INT < 28) {
        MediaStore.Images.Media.getBitmap(contentResolver, this)
    } else {
        val source = ImageDecoder.createSource(contentResolver, this)
        ImageDecoder.decodeBitmap(source);
    }
    return bitmap
}

fun Uri.toBitmapOrNull(context: Context): Bitmap? {
    return try {
        toBitmap(context)
    } catch (e: Throwable) {
        e.printStackTrace()
        null
    }
}