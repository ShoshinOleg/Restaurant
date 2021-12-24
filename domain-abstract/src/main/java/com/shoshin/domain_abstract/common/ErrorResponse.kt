package com.shoshin.domain_abstract.common

class ErrorResponse (
    val errors: List<CommonError>? = null
) {
    constructor(vararg errors: CommonError)
        : this(errors.toList())
}