package com.github.lucasschwenke.simian.domain.validations

interface DnaValidator {

    fun isValid(dna: Array<String>, size: Int): Boolean
}
