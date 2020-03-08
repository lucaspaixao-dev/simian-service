package componentTests

import com.github.lucasschwenke.simian.application.config.DatabaseConfig
import com.github.lucasschwenke.simian.application.config.ObjectMapperConfig
import com.mongodb.BasicDBObject
import com.mongodb.MongoClient
import com.mongodb.ServerAddress
import de.bwaldvogel.mongo.MongoServer
import de.bwaldvogel.mongo.backend.memory.MemoryBackend
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.koin.core.module.Module
import org.koin.dsl.module

abstract class ComponentTest {

    companion object {
        private const val DB_NAME = "fake-db"
    }

    protected val objectMapper = ObjectMapperConfig.configure()

    private val server: MongoServer = MongoServer(MemoryBackend())
    private val client: MongoClient = MongoClient(ServerAddress(server.bind()))

    private val databaseConfig = mockk<DatabaseConfig>(relaxed = true)

    @BeforeEach
    fun setup() {
        every { databaseConfig.getDatabase() } returns DB_NAME
    }

    @AfterEach
    fun after() {
        val db = client.getDatabase(DB_NAME)
        db.getCollection("dna").deleteMany(BasicDBObject())
    }

    protected fun getTestDbModule(): Module = module {
        single { client }
    }
}