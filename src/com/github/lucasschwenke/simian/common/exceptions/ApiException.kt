package com.github.lucasschwenke.simian.common.exceptions

abstract class ApiException(message: String) : Exception(message) {

    abstract fun httpStatus(): Int
    abstract fun apiError(): ApiError
    abstract fun userResponseMessage(): String

    fun createErrorResponse() =
        ErrorResponse(
            apiError = apiError(),
            message = userResponseMessage()
        )
}
