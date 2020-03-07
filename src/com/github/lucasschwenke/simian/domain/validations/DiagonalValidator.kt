package com.github.lucasschwenke.simian.domain.validations

class DiagonalValidator : AbstractValidator(), DnaValidator {

    override fun isValid(dna: Array<String>, size: Int): Boolean {
        var isSimian = 0

        for (i in 0 until size) {
            val actualMaxPosition = i + maxLength

            if (actualMaxPosition < size) {
                val currentValue = dna[i][i]

                if (currentValue == dna[i + 1][i + 1] &&
                    currentValue == dna[i + 2][i + 2] &&
                    currentValue == dna[actualMaxPosition][actualMaxPosition]
                ) {
                    isSimian++
                }
            }
        }

        return checkSimian(isSimian)
    }
}
