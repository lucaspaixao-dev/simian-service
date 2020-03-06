package com.github.lucasschwenke.simian.domain.validations

import com.github.lucasschwenke.simian.domain.exceptions.InvalidDnaLengthException

class VerticalValidator : AbstractValidator(), DnaValidator {

    override fun isValid(dna: Array<String>, size: Int): Boolean {
        var simiansFound = 0

        for (i in 0 until size) {
            for (j in 0 until size) {
                val actualMaxPosition = j + maxLength

                try {
                    if (actualMaxPosition < size) {
                        if (dna[j][i] == dna[j + 1][i] &&
                            dna[j][i] == dna[j + 2][i] &&
                            dna[j][i] == dna[actualMaxPosition][i]
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
