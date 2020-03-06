package com.github.lucasschwenke.com.github.lucasschwenke.simian.application.web.controllers

import com.github.lucasschwenke.simian.application.web.controllers.SimianController
import com.github.lucasschwenke.simian.application.web.request.DnaRequest
import com.github.lucasschwenke.simian.domain.services.SimianService
import io.ktor.application.ApplicationCall
import io.ktor.http.HttpStatusCode
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SimianControllerTest {
    private val dnaRequest = DnaRequest(dna = listOf("TCP", "ABC"))
    private val call = mockk<ApplicationCall>(relaxed = true)
    private val simianService = mockk<SimianService>(relaxed = true)

    private val simianController = SimianController(simianService)

    @Test
    fun `should return OK status code and true when dna it is a simian`() {
        every { simianService.analyzeDna(dnaRequest.dna.toTypedArray()) } returns true

        val response = simianController.analyze(dnaRequest, call)

        assertThat(response).isNotNull
        assertThat(response.isSimian).isTrue()
        verify { call.response.status(HttpStatusCode.OK) }
    }


    @Test
    fun `should return FORBIDDEN status code and true when dna it is a human`() {
        every { simianService.analyzeDna(dnaRequest.dna.toTypedArray()) } returns false

        val response = simianController.analyze(dnaRequest, call)

        assertThat(response).isNotNull
        assertThat(response.isSimian).isFalse()
        verify { call.response.status(HttpStatusCode.Forbidden) }
    }
}