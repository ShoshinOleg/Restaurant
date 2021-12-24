package com.shoshin.domain_abstract.common

sealed class Reaction<out T> {
    data class Success<out T>(val data: T) : Reaction<T>()
    data class Error(val code: Int? = null, val errorResponse: ErrorResponse? = null) : Reaction<Nothing>()
}
