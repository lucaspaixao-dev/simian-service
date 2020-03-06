package com.github.lucasschwenke.com.github.lucasschwenke.simian.domain.validations

import com.github.lucasschwenke.simian.domain.validations.InvertedDiagonalValidator
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class InvertedDiagonalValidatorTest {

    private val invertedDiagonalValidator = InvertedDiagonalValidator()

    @Test
    fun `should return true when is a simian dna`() {
        val simianDna = listOf("CTGTGA", "CTTGGC", "TTTTGT", "TGAGGG", "CCCCTA", "TCACTG").toTypedArray()

        val result = invertedDiagonalValidator.isValid(simianDna, simianDna.size)
        assertThat(result).isTrue()
    }

    @Test
    fun `should return two trues and a false when there are two simian dna and a human dna`() {
        val firstSimianDna = listOf("CTGTGA", "CTTGGC", "TTTTGT", "TGAGGG", "CCCCTA", "TCACTG").toTypedArray()
        val secondSimianDna = listOf("CTGAGA", "CTAGGC", "TAGTGT", "AGAGGG", "CCCCGA", "TTTTAG").toTypedArray()
        val humanDna = listOf("CTGAGA", "CTGAGC", "TATTGT", "AGAGAG", "CACGTA", "TCACTG").toTypedArray()

        val firstResult = invertedDiagonalValidator.isValid(firstSimianDna, firstSimianDna.size)
        assertThat(firstResult).isTrue()

        val secondResult = invertedDiagonalValidator.isValid(secondSimianDna, secondSimianDna.size)
        assertThat(secondResult).isTrue()

        val thirdResult = invertedDiagonalValidator.isValid(humanDna, humanDna.size)
        assertThat(thirdResult).isFalse()
    }

    @Test
    fun `should return false when is a human dna`() {
        val humanDna = listOf("CTGAGA", "CTGAGC", "TATTGT", "AGAGAG", "CACGTA", "TCACTG").toTypedArray()

        val result = invertedDiagonalValidator.isValid(humanDna, humanDna.size)
        assertThat(result).isFalse()
    }
}
