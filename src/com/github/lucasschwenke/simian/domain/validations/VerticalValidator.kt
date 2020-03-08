package com.github.lucasschwenke.simian.domain.validations

import com.github.lucasschwenke.simian.common.logger.ApplicationLogger
import com.github.lucasschwenke.simian.domain.exceptions.InvalidDnaLengthException

class VerticalValidator : AbstractValidator(), DnaValidator {

    companion object : ApplicationLogger()

    override fun isValid(dna: Array<String>, size: Int): Boolean {
        logger.debug("Checking if dna is simian on vertical...")

        var simiansFound = 0
        var actualMaxPosition = 0

        for (i in 0 until size) {
            actualMaxPosition = 0

            for (j in 0 until size) {
                actualMaxPosition = j + maxLength

                try {
                    if (actualMaxPosition < size) {
                        if (dna[j][i] == dna[j + 1][i] &&
                            dna[j][i] == dna[j + 2][i] &&
                            dna[j][i] == dna[actualMaxPosition][i]
                        ) {
                            simiansFound++
                        }
                    }
                } catch (e: IndexOutOfBoundsException) {
                    logger.error("Error on vertical analyze: ", e)
                    throw InvalidDnaLengthException("The DNA contains a invalid length.")
                }
            }
        }

        logger.debug("Simians found in inverted vertical: $simiansFound")
        return checkSimian(simiansFound)
    }
}
