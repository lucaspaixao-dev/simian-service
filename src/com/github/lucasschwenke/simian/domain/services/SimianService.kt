package com.github.lucasschwenke.simian.domain.services

import com.github.lucasschwenke.simian.domain.validations.Validations

class SimianService(private val validations: Validations) {

    /* Here I used Array<String> because in kotlin String[] has changed by Array<String> */
    fun isSimian(dna: Array<String>) : Boolean {
        val listOfValidations = validations.getValidations()
        var isSimian = false

        listOfValidations.find { it.isValid(dna, dna.size) }?.run {
            isSimian = true
        }

        return isSimian
    }
}
