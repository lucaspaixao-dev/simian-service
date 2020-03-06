package com.github.lucasschwenke.simian.application.web.controllers

import com.github.lucasschwenke.simian.application.web.request.DnaRequest
import com.github.lucasschwenke.simian.application.web.response.DnaResponse
import com.github.lucasschwenke.simian.domain.services.SimianService
import io.ktor.application.ApplicationCall
import io.ktor.http.HttpStatusCode

class SimianController(private val simianService: SimianService) {

    fun analyze(dnaRequest: DnaRequest, call: ApplicationCall) : DnaResponse {
        dnaRequest.validate()
        val isSimian = simianService.isSimian(dnaRequest.dna.toTypedArray())

        if (isSimian) {
            call.response.status(HttpStatusCode.OK)
        } else {
            call.response.status(HttpStatusCode.Forbidden)
        }

        return DnaResponse(isSimian)
    }
}

