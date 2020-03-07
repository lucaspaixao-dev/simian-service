package com.github.lucasschwenke.simian.domain.services

import com.github.lucasschwenke.simian.application.web.exceptions.DnaAlreadyRegisteredException
import com.github.lucasschwenke.simian.domain.dna.Dna
import com.github.lucasschwenke.simian.domain.dna.DnaType
import com.github.lucasschwenke.simian.domain.dna.repositories.DnaRepository
import com.github.lucasschwenke.simian.domain.validations.Validations

class DnaService(
    private val validations: Validations,
    private val dnaRepository: DnaRepository
) {

    /* Here I used Array<String> because in kotlin String[] has changed by Array<String> */
    fun isSimian(dna: Array<String>): Boolean {
        checkExistsDna(dna)
        val simianFound = validateDna(dna)

        return simianFound.also { saveDna(dna, simianFound) }
    }

    private fun checkExistsDna(dna: Array<String>) {
        if (dnaRepository.exists(dna)) {
            throw DnaAlreadyRegisteredException("DNA already registered.")
        }
    }

    private fun validateDna(dna: Array<String>): Boolean {
        val listOfValidations = validations.getValidations()
        var simian = false

        listOfValidations.find { it.isValid(dna, dna.size) }?.run {
            simian = true
        }

        return simian
    }

    private fun saveDna(dna: Array<String>, simianFound: Boolean) {
        val newDna = Dna(
            dna = dna,
            type = if (simianFound) DnaType.SIMIAN else DnaType.HUMAN
        )

        dnaRepository.create(newDna)
    }
}
