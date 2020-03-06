package com.github.lucasschwenke.simian.domain.validations

class InvertedDiagonalValidator : AbstractValidator(), DnaValidator {

    override fun isValid(dna: Array<String>, size: Int): Boolean {
        var isSimian = 0

        for (i in 0 until size) {
            val initialPosition = i + maxLength

            if (initialPosition < size) {
                val currentValue = dna[initialPosition][i]

                if (
                    currentValue == dna[initialPosition - 1][i + 1] &&
                    currentValue == dna[initialPosition - 2][i + 2] &&
                    currentValue == dna[initialPosition - 3][i + 3]
                ) {
                    isSimian ++
                }
            }
        }

        return checkSimian(isSimian)
    }
}
