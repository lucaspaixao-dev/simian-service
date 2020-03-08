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

class StatsControllerTest {

    private val call = mockk<ApplicationCall>(relaxed = true)
    private val statsService = mockk<StatsService>(relaxed = true)
    private val statsController = StatsController(statsService)

    @Test
    fun `should return 200 status when get the radio`() {
        every { statsService.stats() } returns Triple(1, 1, BigDecimal.ONE)

        val result = statsController.getCurrentStats(call)

        with(result) {
            assertThat(this.countHumanDna).isEqualTo(1)
            assertThat(this.countMutantDna).isEqualTo(1)
            assertThat(this.radio).isEqualTo(BigDecimal.ONE)
        }

        verify { call.response.status(HttpStatusCode.OK) }
    }
}
