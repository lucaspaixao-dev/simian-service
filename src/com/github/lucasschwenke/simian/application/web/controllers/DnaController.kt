package com.github.lucasschwenke.simian.application.web.controllers

import com.github.lucasschwenke.simian.application.web.request.DnaRequest
import com.github.lucasschwenke.simian.application.web.response.DnaResponse
import com.github.lucasschwenke.simian.domain.services.DnaService
import io.ktor.application.ApplicationCall
import io.ktor.http.HttpStatusCode

class DnaController(private val dnaService: DnaService) {

    fun analyze(dnaRequest: DnaRequest, call: ApplicationCall) : DnaResponse {
        dnaRequest.validate()
        val isSimian = dnaService.isSimian(dnaRequest.dna.toTypedArray())

        if (isSimian) {
            call.response.status(HttpStatusCode.OK)
        } else {
            call.response.status(HttpStatusCode.Forbidden)
        }

        return DnaResponse(isSimian)
    }
}

