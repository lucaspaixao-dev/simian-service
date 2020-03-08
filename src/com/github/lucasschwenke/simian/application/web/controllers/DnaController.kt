package com.github.lucasschwenke.simian.application.web.controllers

import com.github.lucasschwenke.simian.application.web.request.DnaRequest
import com.github.lucasschwenke.simian.application.web.response.DnaResponse
import com.github.lucasschwenke.simian.common.logger.ApplicationLogger
import com.github.lucasschwenke.simian.domain.services.DnaService
import io.ktor.application.ApplicationCall
import io.ktor.http.HttpStatusCode

class DnaController(private val dnaService: DnaService) {

    companion object : ApplicationLogger()

    fun analyze(dnaRequest: DnaRequest, call: ApplicationCall): DnaResponse {
        dnaRequest.validate()
        val isSimian = dnaService.isSimian(dnaRequest.dna.toTypedArray())

        val dnaResponse = DnaResponse(isSimian)
        if (isSimian) {
            logger.debug("Responding 200 OK in analyze with dna $dnaResponse")
            call.response.status(HttpStatusCode.OK)
        } else {
            logger.debug("Responding 403 Forbidden in analyze with dna $dnaResponse")
            call.response.status(HttpStatusCode.Forbidden)
        }

        return dnaResponse
    }
}
