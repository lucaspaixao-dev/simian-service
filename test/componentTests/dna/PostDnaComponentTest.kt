package componentTests.dna

import com.github.lucasschwenke.simian.application.module
import componentTests.ComponentTest
import componentTests.utils.ComponentTestsUtils.readFile
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
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(readFile("requests/human_dna"))
            }.apply {
                assertThat(this.response.status()).isNotNull
                assertThat(this.response.status()!!.value).isEqualTo(HttpStatusCode.Forbidden.value)
                assertThat(this.response.content).isNotNull()

                val expectedResponse = readFile("responses/human_dna")
                assertEquals(expectedResponse, this.response.content, JSONCompareMode.LENIENT)
            }
        }
    }

    @Test
    fun `should return 400 status when dna is already registered`() {
        withTestApplication({ module(dbTestModule = getTestDbModule()) }) {
            handleRequest(HttpMethod.Post, "/simian") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(readFile("requests/simian_dna"))
            }

            handleRequest(HttpMethod.Post, "/simian") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(readFile("requests/simian_dna"))
            }.apply {
                assertThat(this.response.status()).isNotNull
                assertThat(this.response.status()!!.value).isEqualTo(HttpStatusCode.BadRequest.value)
                assertThat(this.response.content).isNotNull()

                val expectedResponse = readFile("responses/dna_already_registered_error")
                assertEquals(expectedResponse, this.response.content, JSONCompareMode.LENIENT)
            }
        }
    }

    @Test
    fun `should return 400 status when dna contains is a invalid format`() {
        withTestApplication({ module(dbTestModule = getTestDbModule()) }) {
            handleRequest(HttpMethod.Post, "/simian") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(readFile("requests/invalid_format_dna"))
            }.apply {
                assertThat(this.response.status()).isNotNull
                assertThat(this.response.status()!!.value).isEqualTo(HttpStatusCode.BadRequest.value)
                assertThat(this.response.content).isNotNull()

                val expectedResponse = readFile("responses/dna_contains_invalid_format_error")
                assertEquals(expectedResponse, this.response.content, JSONCompareMode.LENIENT)
            }
        }
    }

    @Test
    fun `should return 400 status when dna contains invalid characters`() {
        withTestApplication({ module(dbTestModule = getTestDbModule()) }) {
            handleRequest(HttpMethod.Post, "/simian") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(readFile("requests/invalid_letter_dna"))
            }.apply {
                assertThat(this.response.status()).isNotNull
                assertThat(this.response.status()!!.value).isEqualTo(HttpStatusCode.BadRequest.value)
                assertThat(this.response.content).isNotNull()

                val expectedResponse = readFile("responses/dna_invalid_letter_error")
                assertEquals(expectedResponse, this.response.content, JSONCompareMode.LENIENT)
            }
        }
    }

    @Test
    fun `should return 400 status when dna contains lower case letters`() {
        withTestApplication({ module(dbTestModule = getTestDbModule()) }) {
            handleRequest(HttpMethod.Post, "/simian") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(readFile("requests/lower_case_dna"))
            }.apply {
                assertThat(this.response.status()).isNotNull
                assertThat(this.response.status()!!.value).isEqualTo(HttpStatusCode.BadRequest.value)
                assertThat(this.response.content).isNotNull()

                val expectedResponse = readFile("responses/dna_invalid_lower_case_error")
                assertEquals(expectedResponse, this.response.content, JSONCompareMode.LENIENT)
            }
        }
    }
}
