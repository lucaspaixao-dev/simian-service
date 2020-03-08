package componentTests.dna

import com.fasterxml.jackson.module.kotlin.readValue
import com.github.lucasschwenke.simian.application.module
import com.github.lucasschwenke.simian.application.web.request.DnaRequest
import com.github.lucasschwenke.simian.application.web.response.DnaResponse
import componentTests.ComponentTest
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import io.ktor.server.testing.withTestApplication
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PostHorizontalDnaComponentTest : ComponentTest() {

    @Test
    fun `should return 200 status when a simian dna in horizontal`() {
        withTestApplication({ module(dbTestModule = getTestDbModule()) }) {
            handleRequest(HttpMethod.Post, "/simian") {
                val request = DnaRequest(dna = listOf("CCCCCA", "CTGAGC", "TATTGT", "AGAGAG", "CACGTA", "TCACTG"))

                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(objectMapper.writeValueAsString(request))
            }.apply {
                assertThat(this.response.status()).isNotNull
                assertThat(this.response.status()!!.value).isEqualTo(HttpStatusCode.OK.value)
                assertThat(this.response.content).isNotNull()

                val response = objectMapper.readValue<DnaResponse>(this.response.content!!)
                assertThat(response.simian).isTrue()
            }
        }
    }
}