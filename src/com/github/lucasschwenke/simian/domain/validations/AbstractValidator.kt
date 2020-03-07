package com.github.lucasschwenke.simian.domain.validations

abstract class AbstractValidator {

    companion object {
        private const val MAX_LENGTH = 3
        private const val SIMIANS_FOUND = 1
    }

    val maxLength = MAX_LENGTH

    protected fun checkSimian(simiansFound: Int): Boolean = simiansFound >= SIMIANS_FOUND
}