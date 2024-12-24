package com.themoviedbdemo.network

import com.themoviedbdemo.models.responcemodel.ApiResponse
import com.themoviedbdemo.models.responcemodel.Person
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {

    @GET("person/popular")
    suspend fun getPopularPersons(
        @Query("language") language: String,
        @Query("page") page: Int
    ): ApiResponse<Person>

    @GET("search/person")
    suspend fun searchPersons(
        @Query("query") query: String,
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int,
    ): ApiResponse<Person>
}