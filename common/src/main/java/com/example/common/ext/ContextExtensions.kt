package com.example.common.ext

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.provider.Settings
import com.example.common.BuildConfig

fun Context.isDeveloperOptionsEnabled(): Boolean {
    return !BuildConfig.DEBUG && Settings.Secure.getInt(this.contentResolver, Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0) == 1
}

 fun Context.loadFromAssetsToBitmap(filePath: String): Bitmap? {
    var bitmap: Bitmap? = null
    try {
        val inputStream = this.assets.open(filePath)
        bitmap = BitmapFactory.decodeStream(inputStream)
        inputStream.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return bitmap
}
 fun Context.dpToPx(size:Int): Int {
    val density = resources.displayMetrics.density
    return (size * density).toInt()
}