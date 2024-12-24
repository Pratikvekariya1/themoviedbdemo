package com.themoviedbdemo.models.responcemodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class ApiResponse<T>(
    val success: Boolean? = true, // Null for success responses
    val status_code: Int? = null, // Present in failure
    val status_message: String? = null, // Present in failure
    val page: Int? = null, // Present in success
    val results: List<@RawValue T>? = null, // List of results in success
    val total_pages: Int? = null, // Present in success
    val total_results: Int? = null // Present in success
): Parcelable

@Parcelize
data class Person(
    val id: Int,
    val name: String,
    val popularity: Double,
    val profile_path: String?,
    val known_for: List<@RawValue KnownFor>
):Parcelable
@Parcelize
data class KnownFor(
    val id: Int,
    val title: String?,
    val name: String?,
    val overview: String,
    val poster_path: String?,
    val media_type: String,
    val vote_average: Double,
    val vote_count: Int
):Parcelable
