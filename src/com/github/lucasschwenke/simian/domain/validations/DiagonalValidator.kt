package com.github.lucasschwenke.simian.domain.validations

import com.github.lucasschwenke.simian.common.logger.ApplicationLogger

class DiagonalValidator : AbstractValidator(), DnaValidator {

    companion object : ApplicationLogger()

    override fun isValid(dna: Array<String>, size: Int): Boolean {
        logger.debug("Checking if dna is simian on diagonal...")
        var simiansFound = 0

        for (i in 0 until size) {
            val actualMaxPosition = i + maxLength

            if (actualMaxPosition < size) {
                val currentValue = dna[i][i]

                if (currentValue == dna[i + 1][i + 1] &&
                    currentValue == dna[i + 2][i + 2] &&
                    currentValue == dna[actualMaxPosition][actualMaxPosition]
                ) {
                    simiansFound++
                }
            }
        }

        logger.debug("Simians found in diagonal: $simiansFound")
        return checkSimian(simiansFound)
    }
}
