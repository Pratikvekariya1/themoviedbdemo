package com.themoviedbdemo.network

import android.content.Context
import com.themoviedbdemo.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://api.themoviedb.org/3/"

    fun getInstance(context:Context): ApiInterface {
        val client = OkHttpClient()
        val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val clientBuilder: OkHttpClient.Builder =
            client.newBuilder()
                .addInterceptor(Interceptor.invoke {
                    val request = it.request().newBuilder()
                        .addHeader("accept","application/json")
                        .addHeader("Authorization","Bearer ${BuildConfig.Token}")
                        .build()
                    it.proceed(request)
                })
                .addInterceptor(ConnectivityInterceptor(context))
                .addInterceptor(interceptor as HttpLoggingInterceptor)


        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(clientBuilder.build())
            .build()
            .create(ApiInterface::class.java)
    }
}