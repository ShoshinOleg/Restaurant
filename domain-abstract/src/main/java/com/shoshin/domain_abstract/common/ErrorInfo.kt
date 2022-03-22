package com.shoshin.domain_abstract.common

import java.io.Serializable

class ErrorInfo(
    val code: Int,
    val message: String
): Serializable