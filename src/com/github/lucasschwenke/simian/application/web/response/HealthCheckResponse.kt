package com.github.lucasschwenke.simian.application.web.response

data class HealthCheckResponse(
    val database: String,
    val application: String
)
