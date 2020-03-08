package com.github.lucasschwenke.simian.domain.validations

import com.github.lucasschwenke.simian.domain.exceptions.InvalidDnaLengthException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class HorizontalValidatorTest {

    private val horizontalValidator = HorizontalValidator()

    @Test
    fun `should return true when is a simian dna`() {
        val simianDna = listOf("CTGAGA", "CTAGGC", "TATTGT", "AGAGGG", "CCCCTA", "TCACTG").toTypedArray()

        val result = horizontalValidator.isValid(simianDna, simianDna.size)
        assertThat(result).isTrue()
    }

    @Test
    fun `should return two trues and a false when there are two simian dna and a human dna`() {
        val firstSimianDna = listOf("CTGAGA", "CTAGGC", "TATTGT", "AGAGGG", "CCCCTA", "TCACTG").toTypedArray()
        val secondSimianDna = listOf("CTGAGA", "CTAGGC", "TATTGT", "AGAGGG", "CCCCTA", "TTTTAG").toTypedArray()
        val humanDna = listOf("CTGAGA", "CTGAGC", "TATTGT", "AGAGAG", "CACGTA", "TCACTG").toTypedArray()

        val firstResult = horizontalValidator.isValid(firstSimianDna, firstSimianDna.size)
        assertThat(firstResult).isTrue()

        val secondResult = horizontalValidator.isValid(secondSimianDna, secondSimianDna.size)
        assertThat(secondResult).isTrue()

        val thirdResult = horizontalValidator.isValid(humanDna, humanDna.size)
        assertThat(thirdResult).isFalse()
    }

    @Test
    fun `should return false when is a human dna`() {
        val humanDna = listOf("CTGAGA", "CTGAGC", "TATTGT", "AGAGAG", "CACGTA", "TCACTG").toTypedArray()

        val result = horizontalValidator.isValid(humanDna, humanDna.size)
        assertThat(result).isFalse()
    }

    @Test
    fun `should throws an exception when dna contains a invalid length`() {
        val invalidDna = listOf("CAGA", "CGGC", "TATTGT", "AGGG", "CCCCTA", "TTTTAAG").toTypedArray()

        assertThrows(InvalidDnaLengthException::class.java) {
            horizontalValidator.isValid(invalidDna, invalidDna.size)
        }
    }
}
