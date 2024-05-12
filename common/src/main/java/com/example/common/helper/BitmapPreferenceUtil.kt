package com.example.common.helper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import android.util.Base64
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

object BitmapPreferenceUtil {
    private const val PREF_NAME = "bitmap_preference"

    fun saveBitmap(context: Context, key:String, bitmap: Bitmap) {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        val encodedBitmap: String = Base64.encodeToString(byteArray, Base64.DEFAULT)
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(key, encodedBitmap)
        editor.apply()
    }

    fun getBitmap(context: Context, key:String): Bitmap? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val encodedBitmap = sharedPreferences.getString(key, null)
        if (encodedBitmap != null) {
            val byteArray: ByteArray = Base64.decode(encodedBitmap, Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        }
        return null
    }

    fun convertFirstPageToBitmap(pdfByteArray: ByteArray): Bitmap? {
        var bitmap: Bitmap? = null
        val inputStream = ByteArrayInputStream(pdfByteArray)
        val tempFile = createTempFile()
        val fileOutputStream = FileOutputStream(tempFile)

        try {
            inputStream.copyTo(fileOutputStream)
            val parcelFileDescriptor = ParcelFileDescriptor.open(tempFile, ParcelFileDescriptor.MODE_READ_ONLY)
            val pdfRenderer = PdfRenderer(parcelFileDescriptor)
            if (pdfRenderer.pageCount > 0) {
                val page = pdfRenderer.openPage(0)
                val width = 150
                val height =150
                bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
                page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                page.close()
            }
            pdfRenderer.close()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            inputStream.close()
            fileOutputStream.close()
            tempFile.delete()
        }

        return bitmap
    }

    private fun createTempFile(): File {
        return File.createTempFile("temp_pdf", null,)
    }
}