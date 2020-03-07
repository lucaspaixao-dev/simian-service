package com.github.lucasschwenke.simian.domain.services

import com.github.lucasschwenke.simian.domain.dna.DnaType
import com.github.lucasschwenke.simian.domain.dna.DnaType.HUMAN
import com.github.lucasschwenke.simian.domain.dna.DnaType.SIMIAN
import com.github.lucasschwenke.simian.domain.dna.repositories.DnaRepository
import java.math.BigDecimal

class StatsService(private val dnaRepository: DnaRepository) {

    fun stats(): Triple<Int, Int, BigDecimal> {
        val simianFound = quantityOfType(SIMIAN)
        val humanFound = quantityOfType(HUMAN)

        val ratio = calculate(simianFound, humanFound)

        return Triple(simianFound, humanFound, ratio)
    }

    private fun calculate(simian: Int, human: Int): BigDecimal {
        val simianBigDecimal = BigDecimal(simian)
        val humanBigDecimal = BigDecimal(human)

        if (humanBigDecimal == BigDecimal.ZERO) {
            return simianBigDecimal
        }

        return simianBigDecimal.divide(humanBigDecimal)
    }

    private fun quantityOfType(dnaType: DnaType): Int = dnaRepository.countByType(dnaType)
}
