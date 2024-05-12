package com.example.common.network

import com.example.common.exception.ConnectionException
import com.example.common.exception.ServerException
import okhttp3.Interceptor
import okhttp3.Response
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class NetworkErrorInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            val request = chain.request()
            val response = chain.proceed(request)

            // Check if the response indicates an error (HTTP status code in the 4xx or 5xx range)
            if (!response.isSuccessful) {
                // You can customize error handling based on the HTTP status code
                when (response.code) {
                    400 -> {
                        // Handle Bad Request
                    }
                    401 -> {
                        // Handle Unauthorized
                    }
                    404 -> {
                        // Handle Not Found
                    }
                    // Add more cases for other status codes as needed
                    else -> {
                        // Handle other error cases
                    }
                }
            }
            return response
        }catch (e: Exception) {
            when(e) {
                is SocketException,
                is UnknownHostException,
                is SocketTimeoutException-> {
                    val message = "Uh oh! The application is unable to connect to our servers. " +
                            "Kindly check if you are connected to the internet and try again."
                    throw ConnectionException(message, e)
                }
                else -> {

                    val message = "Sorry, something went wrong. " +
                            "We’re working on it and we’ll get it fixed as soon as we can. Ref: C1"
                    throw ServerException(message, e)
                }
            }
        }
    }
}
