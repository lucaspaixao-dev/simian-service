package com.github.lucasschwenke.simian.domain.validations

import com.github.lucasschwenke.simian.common.logger.ApplicationLogger

class InvertedDiagonalValidator : AbstractValidator(), DnaValidator {

    companion object : ApplicationLogger()

    override fun isValid(dna: Array<String>, size: Int): Boolean {
        logger.debug("Checking if dna is simian on inverted diagonal...")
        var simiansFound = 0

        for (i in 0 until size) {
            val initialPosition = i + maxLength

            if (initialPosition < size) {
                val currentValue = dna[initialPosition][i]

                if (
                    currentValue == dna[initialPosition - 1][i + 1] &&
                    currentValue == dna[initialPosition - 2][i + 2] &&
                    currentValue == dna[initialPosition - 3][i + 3]
                ) {
                    simiansFound++
                }
            }
        }

        logger.debug("Simians found in inverted diagonal: $simiansFound")
        return checkSimian(simiansFound)
    }
}
