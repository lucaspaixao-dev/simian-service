package com.github.lucasschwenke.com.github.lucasschwenke.simian.domain.validations

import com.github.lucasschwenke.simian.domain.validations.DiagonalValidator
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class DiagonalValidatorTest {

    private val diagonalValidator = DiagonalValidator()

    @Test
    fun `should return true when is a simian dna`() {
        val simianDna = listOf("CTGAGA", "CCAGGC", "TACTGT", "AGACGG", "CCCCTA", "TCACTG").toTypedArray()

        val result = diagonalValidator.isValid(simianDna, simianDna.size)
        assertThat(result).isTrue()
    }

    @Test
    fun `should return two trues and a false when there are two simian dna and a human dna`() {
        val firstSimianDna = listOf("CTGAGA", "CCAGGC", "TACTGT", "AGACGG", "CCCCTA", "TCACTG").toTypedArray()
        val secondSimianDna = listOf("CTGAGA", "CCTGGC", "TACTGT", "AGACTG", "CCCCTA", "TCACTG").toTypedArray()
        val humanDna = listOf("CTGAGA", "CTGAGC", "TATTGT", "AGAGAG", "CACGTA", "TCACTG").toTypedArray()

        val firstResult = diagonalValidator.isValid(firstSimianDna, firstSimianDna.size)
        assertThat(firstResult).isTrue()

        val secondResult = diagonalValidator.isValid(secondSimianDna, secondSimianDna.size)
        assertThat(secondResult).isTrue()

        val thirdResult = diagonalValidator.isValid(humanDna, humanDna.size)
        assertThat(thirdResult).isFalse()
    }

    @Test
    fun `should return false when is a human dna`() {
        val humanDna = listOf("CTGAGA", "CTGAGC", "TATTGT", "AGAGAG", "CACGTA", "TCACTG").toTypedArray()

        val result = diagonalValidator.isValid(humanDna, humanDna.size)
        assertThat(result).isFalse()
    }
}
