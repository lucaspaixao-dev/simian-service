package com.github.lucasschwenke.simian.common.exceptions

data class ErrorResponse(
    val apiError: ApiError,
    val message: String
)
