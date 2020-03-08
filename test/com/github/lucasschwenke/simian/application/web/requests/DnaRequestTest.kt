package com.github.lucasschwenke.simian.application.web.requests

import com.github.lucasschwenke.simian.application.web.exceptions.InvalidCharacterException
import com.github.lucasschwenke.simian.application.web.request.DnaRequest
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class DnaRequestTest {

    @Test
    fun `should not thrown an exception when there are not a invalid character`() {
        val dnaRequest = DnaRequest(listOf("AAATC", "CCCGG"))

        assertDoesNotThrow { dnaRequest.validate() }
    }

    @Test
    fun `should thrown an exception when there are a invalid  character`() {
        val dnaRequest = DnaRequest(listOf("AAATC", "CCZGG"))

        assertThrows(InvalidCharacterException::class.java) {
            dnaRequest.validate()
        }
    }

    @Test
    fun `should thrown an exception when there are lower case letter`() {
        val dnaRequest = DnaRequest(listOf("AAaTC", "CCZGG"))

        assertThrows(InvalidCharacterException::class.java) {
            dnaRequest.validate()
        }
    }
}