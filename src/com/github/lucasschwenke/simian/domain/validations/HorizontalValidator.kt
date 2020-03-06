package com.github.lucasschwenke.simian.domain.validations

import com.github.lucasschwenke.simian.domain.exceptions.InvalidDnaLengthException

class HorizontalValidator : AbstractValidator(), DnaValidator {

    override fun isValid(dna: Array<String>, size: Int) : Boolean {
        var simiansFound = 0

        for (i in 0 until size) {
            for (j in 0 until size) {
                val actualMaxPosition = j + maxLength

                try {
                    if (actualMaxPosition < size) {
                        val currentValue = dna[i][j]

                        if (currentValue == dna[i][j + 1] &&
                            currentValue == dna[i][j + 2] &&
                            currentValue == dna[i][j + actualMaxPosition]
                        ) {
                            simiansFound ++
                        }
                    }
                } catch (e: IndexOutOfBoundsException) {
                    throw InvalidDnaLengthException("The DNA contains a invalid length.")
                }
            }
        }

        return checkSimian(simiansFound)
    }
}
