package com.github.lucasschwenke.simian.domain.validations

import com.github.lucasschwenke.simian.domain.exceptions.InvalidDnaLengthException

class HorizontalValidator : AbstractValidator(), DnaValidator {

    override fun isValid(dna: Array<String>, size: Int): Boolean {
        var simiansFound = 0
        var actualMaxPosition: Int

        for (i in 0 until size) {
            for (j in 0 until size) {
                actualMaxPosition = j + maxLength

                try {
                    if (actualMaxPosition < size) {
                        val currentValue = dna[i][j]

                        if (currentValue == dna[i][j + 1] &&
                            currentValue == dna[i][j + 2] &&
                            currentValue == dna[i][actualMaxPosition]
                        ) {
                            simiansFound++
                        }
                    }
                } catch (e: IndexOutOfBoundsException) {
                    throw InvalidDnaLengthException("The DNA contains a invalid length.")
                }
            }
            actualMaxPosition = 0
        }

        return checkSimian(simiansFound)
    }
}
