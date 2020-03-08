package com.github.lucasschwenke.simian.application.web.controllers

import com.github.lucasschwenke.simian.application.web.response.StatsResponse
import com.github.lucasschwenke.simian.common.logger.ApplicationLogger
import com.github.lucasschwenke.simian.domain.services.StatsService
import io.ktor.application.ApplicationCall
import io.ktor.http.HttpStatusCode

class StatsController(private val statsService: StatsService) {

    companion object : ApplicationLogger()

    fun getCurrentStats(call: ApplicationCall) =
        statsService.stats()
            .also {
                call.response.status(HttpStatusCode.OK)
            }.let {
                val response = StatsResponse(
                    countMutantDna = it.first,
                    countHumanDna = it.second,
                    radio = it.third
                )

                logger.debug("Responding ratio $response with status 200 OK")
                response
            }
}
