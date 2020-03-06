package com.github.lucasschwenke.simian.common.exceptions

abstract class ApiException : Exception {

    constructor(cause: Throwable) : super(cause)
    constructor(message: String) : super(message)

    abstract fun httpStatus(): Int
    abstract fun apiError(): ApiError
    abstract fun userResponseMessage(): String

    fun createErrorResponse() =
        ErrorResponse(
            apiError = apiError(),
            message = userResponseMessage()
        )
}
