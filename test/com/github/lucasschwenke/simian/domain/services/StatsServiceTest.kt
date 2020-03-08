package com.github.lucasschwenke.simian.domain.services

import com.github.lucasschwenke.simian.domain.dna.DnaType
import com.github.lucasschwenke.simian.domain.dna.repositories.DnaRepository
import com.github.lucasschwenke.simian.domain.services.StatsService
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class StatsServiceTest {

    private val dnaRepository = mockk<DnaRepository>(relaxed = true)
    private val statsService = StatsService(dnaRepository)

    @Test
    fun `should return ratio 05 when there are two human and a simian dna`() {
        every { dnaRepository.countByType(DnaType.HUMAN) } returns 2
        every { dnaRepository.countByType(DnaType.SIMIAN) } returns 1

        val stats = statsService.stats()

        with(stats) {
            assertThat(this.first).isEqualTo(1)
            assertThat(this.second).isEqualTo(2)
            assertThat(this.third).isEqualTo(BigDecimal(0.5))
        }
    }

    @Test
    fun `should return simian count when human is equal to 0`() {
        every { dnaRepository.countByType(DnaType.HUMAN) } returns 0
        every { dnaRepository.countByType(DnaType.SIMIAN) } returns 10

        val stats = statsService.stats()

        with(stats) {
            assertThat(this.first).isEqualTo(10)
            assertThat(this.second).isEqualTo(0)
            assertThat(this.third).isEqualTo(BigDecimal(10))
        }
    }

    @Test
    fun `should return simian 1 when human is equal to simian`() {
        every { dnaRepository.countByType(DnaType.HUMAN) } returns 2
        every { dnaRepository.countByType(DnaType.SIMIAN) } returns 2

        val stats = statsService.stats()

        with(stats) {
            assertThat(this.first).isEqualTo(2)
            assertThat(this.second).isEqualTo(2)
            assertThat(this.third).isEqualTo(BigDecimal(1))
        }
    }
}