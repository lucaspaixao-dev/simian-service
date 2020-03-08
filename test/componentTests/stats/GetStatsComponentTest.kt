package componentTests.stats

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

class GetStatsComponentTest : ComponentTest() {

    @Test
    fun `should return 200 status when ratio is equal to 05`() {
        withTestApplication({ module(dbTestModule = getTestDbModule()) }) {
            handleRequest(HttpMethod.Post, "/simian") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(readFile("requests/human_dna"))
            }

            handleRequest(HttpMethod.Post, "/simian") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(readFile("requests/other_human_dna"))
            }

            handleRequest(HttpMethod.Post, "/simian") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(readFile("requests/simian_dna"))
            }

            handleRequest(HttpMethod.Get, "/stats") {}.apply {
                assertThat(this.response.status()).isNotNull
                assertThat(this.response.status()!!.value).isEqualTo(HttpStatusCode.OK.value)
                assertThat(this.response.content).isNotNull()

                val expectedResponse = readFile("responses/ratio_05")
                assertEquals(expectedResponse, this.response.content, JSONCompareMode.LENIENT)
            }
        }
    }
}