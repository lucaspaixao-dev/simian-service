package com.github.lucasschwenke.simian.application.web.controllers

import com.github.lucasschwenke.simian.application.web.response.HealthCheckResponse
import com.github.lucasschwenke.simian.domain.services.StatsService
import io.ktor.application.ApplicationCall
import io.ktor.http.HttpStatusCode

class HealthCheckController(private val statsService: StatsService) {

    fun getHealthCheck(call: ApplicationCall) =
        statsService.stats().also {
            call.response.status(HttpStatusCode.OK)
        }.let {
            HealthCheckResponse(
                database = "OK",
                application = "OK"
            )
        }
}
