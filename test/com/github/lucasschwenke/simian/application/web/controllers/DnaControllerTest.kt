package com.github.lucasschwenke.simian.application.web.controllers

import com.github.lucasschwenke.simian.application.web.request.DnaRequest
import com.github.lucasschwenke.simian.domain.services.DnaService
import io.ktor.application.ApplicationCall
import io.ktor.http.HttpStatusCode
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class DnaControllerTest {
    private val dnaRequest = DnaRequest(dna = listOf("ATCG", "ATCG"))
    private val call = mockk<ApplicationCall>(relaxed = true)
    private val simianService = mockk<DnaService>(relaxed = true)

    private val simianController = DnaController(simianService)

    @Test
    fun `should return OK status code and true when dna it is a simian`() {
        every { simianService.isSimian(dnaRequest.dna.toTypedArray()) } returns true

        val response = simianController.analyze(dnaRequest, call)

        assertThat(response).isNotNull
        assertThat(response.isSimian).isTrue()
        verify { call.response.status(HttpStatusCode.OK) }
    }


    @Test
    fun `should return FORBIDDEN status code and true when dna it is a human`() {
        every { simianService.isSimian(dnaRequest.dna.toTypedArray()) } returns false

        val response = simianController.analyze(dnaRequest, call)

        assertThat(response).isNotNull
        assertThat(response.isSimian).isFalse()
        verify { call.response.status(HttpStatusCode.Forbidden) }
    }
}