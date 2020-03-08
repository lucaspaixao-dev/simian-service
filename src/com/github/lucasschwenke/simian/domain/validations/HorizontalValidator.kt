package com.github.lucasschwenke.simian.domain.validations

import com.github.lucasschwenke.simian.common.logger.ApplicationLogger
import com.github.lucasschwenke.simian.domain.exceptions.InvalidDnaLengthException

class HorizontalValidator : AbstractValidator(), DnaValidator {

    companion object : ApplicationLogger()

    override fun isValid(dna: Array<String>, size: Int): Boolean {
        logger.debug("Checking if dna is simian on horizontal...")

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
                    logger.error("Error on horizontal analyze: ", e)
                    throw InvalidDnaLengthException("The DNA contains a invalid length.")
                }
            }
            actualMaxPosition = 0
        }

        logger.debug("Simians found in horizontal: $simiansFound")
        return checkSimian(simiansFound)
    }
}
