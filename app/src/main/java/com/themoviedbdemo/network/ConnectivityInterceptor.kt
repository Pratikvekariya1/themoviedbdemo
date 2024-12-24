package com.themoviedbdemo.network

import android.content.Context
import com.themoviedbdemo.utills.NetworkUtil
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ConnectivityInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!NetworkUtil.isInternetAvailable(context)) {
            throw NoConnectivityException() // Custom exception to handle no connectivity
        }
        return chain.proceed(chain.request())
    }
}

class NoConnectivityException : IOException("No Internet Connection")
