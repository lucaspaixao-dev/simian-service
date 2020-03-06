package com.github.lucasschwenke.com.github.lucasschwenke.simian.domain.services

import com.github.lucasschwenke.simian.domain.services.SimianService
import com.github.lucasschwenke.simian.domain.validations.*
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SimianServiceTest {
    
    companion object {
        private val DNA = arrayOf("TAC", "TAC")
    }

    private val validations = mockk<Validations>(relaxed = true)
    private val horizontalValidator = mockk<HorizontalValidator>(relaxed = true)
    private val verticalValidator = mockk<VerticalValidator>(relaxed = true)
    private val diagonalValidator = mockk<DiagonalValidator>(relaxed = true)
    private val invertedDiagonalValidator = mockk<InvertedDiagonalValidator>(relaxed = true)
    private val listOfValidations = listOf<DnaValidator>(
        horizontalValidator,
        verticalValidator,
        diagonalValidator,
        invertedDiagonalValidator
    )

    private val simianService = SimianService(validations)

    @Test
    fun `should return true when find a simian DNA in horizontal validator`() {
        every { validations.getValidations() } returns listOfValidations
        every { horizontalValidator.isValid(DNA, DNA.size) } returns true
        every { verticalValidator.isValid(DNA, DNA.size) } returns false
        every { diagonalValidator.isValid(DNA, DNA.size) } returns false
        every { invertedDiagonalValidator.isValid(DNA, DNA.size) } returns false

        val result = simianService.analyzeDna(DNA)

        assertThat(result).isTrue()
        verify(exactly = 1) { horizontalValidator.isValid(DNA, DNA.size) }
        verify(exactly = 0) { verticalValidator.isValid(DNA, DNA.size) }
        verify(exactly = 0) { diagonalValidator.isValid(DNA, DNA.size) }
        verify(exactly = 0) { invertedDiagonalValidator.isValid(DNA, DNA.size) }
    }

    @Test
    fun `should return true when find a simian DNA in vertical validator`() {
        every { validations.getValidations() } returns listOfValidations
        every { horizontalValidator.isValid(DNA, DNA.size) } returns false
        every { verticalValidator.isValid(DNA, DNA.size) } returns true
        every { diagonalValidator.isValid(DNA, DNA.size) } returns false
        every { invertedDiagonalValidator.isValid(DNA, DNA.size) } returns false

        val result = simianService.analyzeDna(DNA)

        assertThat(result).isTrue()
        verify(exactly = 1) { horizontalValidator.isValid(DNA, DNA.size) }
        verify(exactly = 1) { verticalValidator.isValid(DNA, DNA.size) }
        verify(exactly = 0) { diagonalValidator.isValid(DNA, DNA.size) }
        verify(exactly = 0) { invertedDiagonalValidator.isValid(DNA, DNA.size) }
    }

    @Test
    fun `should return true when find a simian DNA in diagonal validator`() {
        every { validations.getValidations() } returns listOfValidations
        every { horizontalValidator.isValid(DNA, DNA.size) } returns false
        every { verticalValidator.isValid(DNA, DNA.size) } returns false
        every { diagonalValidator.isValid(DNA, DNA.size) } returns true
        every { invertedDiagonalValidator.isValid(DNA, DNA.size) } returns false

        val result = simianService.analyzeDna(DNA)

        assertThat(result).isTrue()
        verify(exactly = 1) { horizontalValidator.isValid(DNA, DNA.size) }
        verify(exactly = 1) { verticalValidator.isValid(DNA, DNA.size) }
        verify(exactly = 1) { diagonalValidator.isValid(DNA, DNA.size) }
        verify(exactly = 0) { invertedDiagonalValidator.isValid(DNA, DNA.size) }
    }

    @Test
    fun `should return true when find a simian DNA in inverted diagonal validator`() {
        every { validations.getValidations() } returns listOfValidations
        every { horizontalValidator.isValid(DNA, DNA.size) } returns false
        every { verticalValidator.isValid(DNA, DNA.size) } returns false
        every { diagonalValidator.isValid(DNA, DNA.size) } returns false
        every { invertedDiagonalValidator.isValid(DNA, DNA.size) } returns true

        val result = simianService.analyzeDna(DNA)

        assertThat(result).isTrue()
        verify(exactly = 1) { horizontalValidator.isValid(DNA, DNA.size) }
        verify(exactly = 1) { verticalValidator.isValid(DNA, DNA.size) }
        verify(exactly = 1) { diagonalValidator.isValid(DNA, DNA.size) }
        verify(exactly = 1) { invertedDiagonalValidator.isValid(DNA, DNA.size) }
    }

    @Test
    fun `should return false when it is a human DNA`() {
        every { validations.getValidations() } returns listOfValidations
        every { horizontalValidator.isValid(DNA, DNA.size) } returns false
        every { verticalValidator.isValid(DNA, DNA.size) } returns false
        every { diagonalValidator.isValid(DNA, DNA.size) } returns false
        every { invertedDiagonalValidator.isValid(DNA, DNA.size) } returns false

        val result = simianService.analyzeDna(DNA)

        assertThat(result).isFalse()
        verify(exactly = 1) { horizontalValidator.isValid(DNA, DNA.size) }
        verify(exactly = 1) { verticalValidator.isValid(DNA, DNA.size) }
        verify(exactly = 1) { diagonalValidator.isValid(DNA, DNA.size) }
        verify(exactly = 1) { invertedDiagonalValidator.isValid(DNA, DNA.size) }
    }
}
