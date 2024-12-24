package com.themoviedbdemo.utills

class CustomException(
    val type: ExceptionType,
    override val message: String
) : Throwable(message) {

    override fun toString(): String {
        return "CustomException(type=$type, message=$message)"
    }
}