package componentTests.dna

import com.fasterxml.jackson.module.kotlin.readValue
import com.github.lucasschwenke.simian.application.module
import com.github.lucasschwenke.simian.application.web.request.DnaRequest
import com.github.lucasschwenke.simian.application.web.response.DnaResponse
import componentTests.ComponentTest
import componentTests.utils.ComponentTestsUtils
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import io.ktor.server.testing.withTestApplication
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert.assertEquals
import org.skyscreamer.jsonassert.JSONCompareMode

class PostDnaComponentTest : ComponentTest() {

    @Test
    fun `should return 403 status when a human dna in horizontal`() {
        withTestApplication({ module(dbTestModule = getTestDbModule()) }) {
            handleRequest(HttpMethod.Post, "/simian") {
                val request = DnaRequest(dna = listOf("CTGAGA", "CTGAGC", "TATTGT", "AGAGAG", "CACGTA", "TCACTG"))

                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(objectMapper.writeValueAsString(request))
            }.apply {
                assertThat(this.response.status()).isNotNull
                assertThat(this.response.status()!!.value).isEqualTo(HttpStatusCode.Forbidden.value)
                assertThat(this.response.content).isNotNull()

                val response = objectMapper.readValue<DnaResponse>(this.response.content!!)
                assertThat(response.simian).isFalse()
            }
        }
    }

    @Test
    fun `should return 400 status when dna is already registered`() {
        withTestApplication({ module(dbTestModule = getTestDbModule()) }) {
            handleRequest(HttpMethod.Post, "/simian") {
                val request = DnaRequest(dna = listOf("CCCCCA", "CTGAGC", "TATTGT", "AGAGAG", "CACGTA", "TCACTG"))
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(objectMapper.writeValueAsString(request))
            }

            handleRequest(HttpMethod.Post, "/simian") {
                val request = DnaRequest(dna = listOf("CCCCCA", "CTGAGC", "TATTGT", "AGAGAG", "CACGTA", "TCACTG"))
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(objectMapper.writeValueAsString(request))
            }.apply {
                assertThat(this.response.status()).isNotNull
                assertThat(this.response.status()!!.value).isEqualTo(HttpStatusCode.BadRequest.value)
                assertThat(this.response.content).isNotNull()

                val expectedResponse = ComponentTestsUtils.readFile("dna_already_registered_error")
                assertEquals(expectedResponse, this.response.content, JSONCompareMode.LENIENT)
            }
        }
    }

    @Test
    fun `should return 400 status when dna contains is a invalid format`() {
        withTestApplication({ module(dbTestModule = getTestDbModule()) }) {
            handleRequest(HttpMethod.Post, "/simian") {
                val request = DnaRequest(dna = listOf("CACA", "CC", "TGT", "AGAG", "CATA", "TCACTG"))
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(objectMapper.writeValueAsString(request))
            }.apply {
                assertThat(this.response.status()).isNotNull
                assertThat(this.response.status()!!.value).isEqualTo(HttpStatusCode.BadRequest.value)
                assertThat(this.response.content).isNotNull()

                val expectedResponse = ComponentTestsUtils.readFile("dna_contains_invalid_format_error")
                assertEquals(expectedResponse, this.response.content, JSONCompareMode.LENIENT)
            }
        }
    }

    @Test
    fun `should return 400 status when dna contains invalid characters`() {
        withTestApplication({ module(dbTestModule = getTestDbModule()) }) {
            handleRequest(HttpMethod.Post, "/simian") {
                val request = DnaRequest(dna = listOf("CTGAGA", "CTGAGC", "TATTGT", "AGAGZG", "CACGTA", "TCACTG"))

                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(objectMapper.writeValueAsString(request))
            }.apply {
                assertThat(this.response.status()).isNotNull
                assertThat(this.response.status()!!.value).isEqualTo(HttpStatusCode.BadRequest.value)
                assertThat(this.response.content).isNotNull()

                val expectedResponse = ComponentTestsUtils.readFile("dna_invalid_letter_error")
                assertEquals(expectedResponse, this.response.content, JSONCompareMode.LENIENT)
            }
        }
    }
}