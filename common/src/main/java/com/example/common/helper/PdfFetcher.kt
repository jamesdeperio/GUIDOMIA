package com.example.common.helper

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException
import java.io.InputStream

class PdfFetcher {
    private val client = OkHttpClient()

    @Throws(IOException::class)
     fun fetchPdf(url: String): InputStream {
        val request = Request.Builder()
            .url(url)
            .build()

        val response: Response = client.newCall(request).execute()
        if (!response.isSuccessful) throw IOException("Unexpected code $response")

        val responseBody: ResponseBody? = response.body
        return responseBody?.byteStream() ?: throw IOException("Empty response body")
    }
}