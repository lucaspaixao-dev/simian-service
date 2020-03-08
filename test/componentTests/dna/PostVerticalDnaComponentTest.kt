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

class PostVerticalDnaComponentTest : ComponentTest() {

    @Test
    fun `should return 200 status when a simian dna in vertical`() {
        withTestApplication({ module(dbTestModule = getTestDbModule()) }) {
            handleRequest(HttpMethod.Post, "/simian") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(readFile("requests/diagonal_simian_dna"))
            }.apply {
                assertThat(this.response.status()).isNotNull
                assertThat(this.response.status()!!.value).isEqualTo(HttpStatusCode.OK.value)
                assertThat(this.response.content).isNotNull()

                val expectedResponse = readFile("responses/simian_dna")
                assertEquals(expectedResponse, this.response.content, JSONCompareMode.LENIENT)
            }
        }
    }
}