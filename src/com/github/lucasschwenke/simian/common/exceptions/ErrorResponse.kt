package com.github.lucasschwenke.simian.common.exceptions

data class ErrorResponse(
    val apiError: ApiError,
    val message: String,
    val details: Map<String, List<String>>? = null
)
