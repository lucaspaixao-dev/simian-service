package componentTests.healthcheck

import com.github.lucasschwenke.simian.application.module
import componentTests.ComponentTest
import componentTests.utils.ComponentTestsUtils.readFile
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert.assertEquals
import org.skyscreamer.jsonassert.JSONCompareMode

class GetHealthCheckComponentTest : ComponentTest() {

    @Test
    fun `should return 200 status in health check`() {
        withTestApplication({ module(dbTestModule = getTestDbModule()) }) {
            handleRequest(HttpMethod.Get, "/health-check") {}.apply {
                Assertions.assertThat(this.response.status()).isNotNull
                Assertions.assertThat(this.response.status()!!.value).isEqualTo(HttpStatusCode.OK.value)
                Assertions.assertThat(this.response.content).isNotNull()

                val expectedResponse = readFile("responses/health_check_ok")
                assertEquals(expectedResponse, this.response.content, JSONCompareMode.LENIENT)
            }
        }
    }
}
