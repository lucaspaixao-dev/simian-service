package com.github.lucasschwenke.simian.domain.services

import com.github.lucasschwenke.simian.application.web.exceptions.DnaAlreadyRegisteredException
import com.github.lucasschwenke.simian.domain.dna.repositories.DnaRepository
import com.github.lucasschwenke.simian.domain.validations.DiagonalValidator
import com.github.lucasschwenke.simian.domain.validations.DnaValidator
import com.github.lucasschwenke.simian.domain.validations.HorizontalValidator
import com.github.lucasschwenke.simian.domain.validations.InvertedDiagonalValidator
import com.github.lucasschwenke.simian.domain.validations.Validations
import com.github.lucasschwenke.simian.domain.validations.VerticalValidator
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class DnaServiceTest {

    companion object {
        private val DNA = arrayOf("TAC", "TAC")
    }

    private val validations = mockk<Validations>(relaxed = true)
    private val dnaRepository = mockk<DnaRepository>(relaxed = true)
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

    private val simianService = DnaService(validations, dnaRepository)

    @Test
    fun `should return true when find a simian DNA in horizontal validator`() {
        every { dnaRepository.exists(DNA) } returns false
        every { validations.getValidations() } returns listOfValidations
        every { horizontalValidator.isValid(DNA, DNA.size) } returns true
        every { verticalValidator.isValid(DNA, DNA.size) } returns false
        every { diagonalValidator.isValid(DNA, DNA.size) } returns false
        every { invertedDiagonalValidator.isValid(DNA, DNA.size) } returns false
        every { dnaRepository.create(any()) } just Runs

        val result = simianService.isSimian(DNA)

        assertThat(result).isTrue()
        verify(exactly = 1) { dnaRepository.exists(DNA) }
        verify(exactly = 1) { horizontalValidator.isValid(DNA, DNA.size) }
        verify(exactly = 0) { verticalValidator.isValid(DNA, DNA.size) }
        verify(exactly = 0) { diagonalValidator.isValid(DNA, DNA.size) }
        verify(exactly = 0) { invertedDiagonalValidator.isValid(DNA, DNA.size) }
        verify(exactly = 1) { dnaRepository.create(any()) }
    }

    @Test
    fun `should return true when find a simian DNA in vertical validator`() {
        every { dnaRepository.exists(DNA) } returns false
        every { validations.getValidations() } returns listOfValidations
        every { horizontalValidator.isValid(DNA, DNA.size) } returns false
        every { verticalValidator.isValid(DNA, DNA.size) } returns true
        every { diagonalValidator.isValid(DNA, DNA.size) } returns false
        every { invertedDiagonalValidator.isValid(DNA, DNA.size) } returns false
        every { dnaRepository.create(any()) } just Runs

        val result = simianService.isSimian(DNA)

        assertThat(result).isTrue()
        verify(exactly = 1) { dnaRepository.exists(DNA) }
        verify(exactly = 1) { horizontalValidator.isValid(DNA, DNA.size) }
        verify(exactly = 1) { verticalValidator.isValid(DNA, DNA.size) }
        verify(exactly = 0) { diagonalValidator.isValid(DNA, DNA.size) }
        verify(exactly = 0) { invertedDiagonalValidator.isValid(DNA, DNA.size) }
        verify(exactly = 1) { dnaRepository.create(any()) }
    }

    @Test
    fun `should return true when find a simian DNA in diagonal validator`() {
        every { dnaRepository.exists(DNA) } returns false
        every { validations.getValidations() } returns listOfValidations
        every { horizontalValidator.isValid(DNA, DNA.size) } returns false
        every { verticalValidator.isValid(DNA, DNA.size) } returns false
        every { diagonalValidator.isValid(DNA, DNA.size) } returns true
        every { invertedDiagonalValidator.isValid(DNA, DNA.size) } returns false
        every { dnaRepository.create(any()) } just Runs

        val result = simianService.isSimian(DNA)

        assertThat(result).isTrue()
        verify(exactly = 1) { dnaRepository.exists(DNA) }
        verify(exactly = 1) { horizontalValidator.isValid(DNA, DNA.size) }
        verify(exactly = 1) { verticalValidator.isValid(DNA, DNA.size) }
        verify(exactly = 1) { diagonalValidator.isValid(DNA, DNA.size) }
        verify(exactly = 0) { invertedDiagonalValidator.isValid(DNA, DNA.size) }
        verify(exactly = 1) { dnaRepository.create(any()) }
    }

    @Test
    fun `should return true when find a simian DNA in inverted diagonal validator`() {
        every { dnaRepository.exists(DNA) } returns false
        every { validations.getValidations() } returns listOfValidations
        every { horizontalValidator.isValid(DNA, DNA.size) } returns false
        every { verticalValidator.isValid(DNA, DNA.size) } returns false
        every { diagonalValidator.isValid(DNA, DNA.size) } returns false
        every { invertedDiagonalValidator.isValid(DNA, DNA.size) } returns true
        every { dnaRepository.create(any()) } just Runs

        val result = simianService.isSimian(DNA)

        assertThat(result).isTrue()
        verify(exactly = 1) { dnaRepository.exists(DNA) }
        verify(exactly = 1) { horizontalValidator.isValid(DNA, DNA.size) }
        verify(exactly = 1) { verticalValidator.isValid(DNA, DNA.size) }
        verify(exactly = 1) { diagonalValidator.isValid(DNA, DNA.size) }
        verify(exactly = 1) { invertedDiagonalValidator.isValid(DNA, DNA.size) }
        verify(exactly = 1) { dnaRepository.create(any()) }
    }

    @Test
    fun `should return false when it is a human DNA`() {
        every { dnaRepository.exists(DNA) } returns false
        every { validations.getValidations() } returns listOfValidations
        every { horizontalValidator.isValid(DNA, DNA.size) } returns false
        every { verticalValidator.isValid(DNA, DNA.size) } returns false
        every { diagonalValidator.isValid(DNA, DNA.size) } returns false
        every { invertedDiagonalValidator.isValid(DNA, DNA.size) } returns false
        every { dnaRepository.create(any()) } just Runs

        val result = simianService.isSimian(DNA)

        assertThat(result).isFalse()
        verify(exactly = 1) { dnaRepository.exists(DNA) }
        verify(exactly = 1) { horizontalValidator.isValid(DNA, DNA.size) }
        verify(exactly = 1) { verticalValidator.isValid(DNA, DNA.size) }
        verify(exactly = 1) { diagonalValidator.isValid(DNA, DNA.size) }
        verify(exactly = 1) { invertedDiagonalValidator.isValid(DNA, DNA.size) }
        verify(exactly = 1) { dnaRepository.create(any()) }
    }

    @Test
    fun `should thrown an exception when dna already registered`() {
        every { dnaRepository.exists(DNA) } returns true

        assertThrows(DnaAlreadyRegisteredException::class.java) {
            simianService.isSimian(DNA)
        }

        verify(exactly = 1) { dnaRepository.exists(DNA) }
        verify(exactly = 0) { horizontalValidator.isValid(DNA, DNA.size) }
        verify(exactly = 0) { verticalValidator.isValid(DNA, DNA.size) }
        verify(exactly = 0) { diagonalValidator.isValid(DNA, DNA.size) }
        verify(exactly = 0) { invertedDiagonalValidator.isValid(DNA, DNA.size) }
        verify(exactly = 0) { dnaRepository.create(any()) }
    }
}
