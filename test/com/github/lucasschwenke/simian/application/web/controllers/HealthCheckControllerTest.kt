package com.github.lucasschwenke.simian.application.web.controllers

import com.github.lucasschwenke.simian.domain.services.StatsService
import io.ktor.application.ApplicationCall
import io.ktor.http.HttpStatusCode
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class HealthCheckControllerTest {

    private val applicationCall = mockk<ApplicationCall>(relaxed = true)
    private val statsService = mockk<StatsService>(relaxed = true)
    private val healthCheckController = HealthCheckController(statsService)

    @Test
    fun `should return health check ok`() {
        every { statsService.stats() } returns Triple(1, 1, BigDecimal(1))

        val response = healthCheckController.getHealthCheck(applicationCall)

        assertThat(response.application).isEqualTo("OK")
        assertThat(response.database).isEqualTo("OK")

        verify { applicationCall.response.status(HttpStatusCode.OK) }
    }
}
