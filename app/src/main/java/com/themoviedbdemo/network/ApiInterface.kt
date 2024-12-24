package com.themoviedbdemo.network

import android.content.Context
import com.themoviedbdemo.models.responcemodel.ApiResponse
import com.themoviedbdemo.models.responcemodel.Person
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface ApiInterface {

    @GET("person/popular")
    suspend fun getPopularPersons(
        @Query("language") language: String,
        @Query("page") page: Int
    ): ApiResponse<Person>
}