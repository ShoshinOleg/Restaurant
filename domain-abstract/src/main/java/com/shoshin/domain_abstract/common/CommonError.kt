package com.shoshin.domain_abstract.common

data class CommonError(
    val code: Int? = null,
    val message: String? = null,
    val link: String? = null
)