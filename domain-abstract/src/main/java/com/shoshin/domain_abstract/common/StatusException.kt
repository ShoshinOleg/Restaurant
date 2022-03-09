package com.shoshin.domain_abstract.common

open class StatusException(
    message: String?,
    open val code: Int,
    cause: Throwable? = null
) : Exception(message, cause)