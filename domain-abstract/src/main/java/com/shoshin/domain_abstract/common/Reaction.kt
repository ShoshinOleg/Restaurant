package com.shoshin.domain_abstract.common

sealed class Reaction<out T> {
    data class Progress<out T>(val data: T? = null) : Reaction<T>()
    data class Success<out T>(val data: T) : Reaction<T>()
    data class Error(val message: String? = null, val exception: StatusException? = null) : Reaction<Nothing>()
}
