package com.github.lucasschwenke.simian.application.web.controllers

import com.github.lucasschwenke.simian.application.web.request.DnaRequest
import com.github.lucasschwenke.simian.application.web.response.DnaResponse
import com.github.lucasschwenke.simian.domain.services.SimianService
import io.ktor.application.ApplicationCall
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive

class SimianController(private val simianService: SimianService) {

    suspend fun analyze(call: ApplicationCall) = call.receive<DnaRequest>()
        .run {
            simianService.analyzeDna(dna.toTypedArray())
        }.also { isSimian ->
            if (isSimian)
                call.response.status(HttpStatusCode.OK)
            else
                call.response.status(HttpStatusCode.Forbidden)
        }.let {
            DnaResponse(it)
        }
}
