package com.shoshin.restaurant.ui.common

import com.shoshin.domain_abstract.common.ErrorResponse


sealed class ViewModelEvent<T> {
    data class Success<T>(val data: T) : ViewModelEvent<T>()
    data class Error<T>(val message: String? = null, val code: Int? = null, val response: ErrorResponse? = null)
        : ViewModelEvent<T>()
    class Download<T> : ViewModelEvent<T>()
}
