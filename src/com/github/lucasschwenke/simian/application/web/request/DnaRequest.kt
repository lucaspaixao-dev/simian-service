package com.github.lucasschwenke.simian.application.web.request

import com.github.lucasschwenke.simian.application.web.exceptions.InvalidCharacterException

data class DnaRequest(val dna: List<String>) {

    private val allowedCharacters = listOf("A", "T", "C", "G")

    fun validate() {
        dna.forEach { sequence ->
            sequence.forEach { letter ->
                validateLowerCase(letter)
                validateBlackList(letter.toString())
            }
        }
    }

    private fun validateBlackList(letter: String) {
        if (!allowedCharacters.contains(letter)) {
            throw InvalidCharacterException(
                "The char $letter in dna it's invalid."
            )
        }
    }

    private fun validateLowerCase(letter: Char) {
        if (letter.isLowerCase()) {
            throw InvalidCharacterException(
                "The char $letter in dna it's invalid."
            )
        }
    }
}
