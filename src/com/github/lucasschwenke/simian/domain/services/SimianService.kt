package com.github.lucasschwenke.simian.domain.services

import com.github.lucasschwenke.simian.domain.validations.Validations

class SimianService(private val validations: Validations) {

    fun analyzeDna(dna: Array<String>) : Boolean {
        val listOfValidations = validations.getValidations()
        var isSimian = false

        listOfValidations.find { it.isValid(dna, dna.size) }?.run {
            isSimian = true
        }

        return isSimian
    }
}
