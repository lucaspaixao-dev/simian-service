package com.github.lucasschwenke.simian.resource.repository.dna

import com.github.lucasschwenke.simian.application.config.ObjectMapperConfig
import com.github.lucasschwenke.simian.domain.dna.Dna
import com.github.lucasschwenke.simian.domain.dna.DnaType
import com.mongodb.MongoClient
import com.mongodb.ServerAddress
import com.mongodb.client.MongoCollection
import de.bwaldvogel.mongo.MongoServer
import de.bwaldvogel.mongo.backend.memory.MemoryBackend
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.bson.Document as BsonDocument

class DnaRepositoryDbTest {

    private val database = "FakeDnaDB"

    private lateinit var client: MongoClient
    private lateinit var server: MongoServer
    private lateinit var dnaRepository: DnaRepositoryDb
    private lateinit var collection: MongoCollection<BsonDocument>

    @BeforeEach
    fun setUp() {
        server = MongoServer(MemoryBackend())
        client = MongoClient(ServerAddress(server.bind()))

        dnaRepository = DnaRepositoryDb(client, database, ObjectMapperConfig.configure())
        collection = client.getDatabase(database).getCollection("dna")
    }

    @AfterEach
    fun tearDown() {
        client.close()
        server.shutdown()
    }

    @Test
    fun `should insert a new dna`() {
        val dna = Dna(
            dna = arrayOf("ABC", "DFG"),
            type = DnaType.HUMAN
        )

        dnaRepository.create(dna)

        val count = collection.find().count()

        assertThat(count).isEqualTo(1)
    }

    @Test
    fun `should return true when dna is already registered`() {
        val dna = Dna(
            dna = arrayOf("ABC", "DFG"),
            type = DnaType.HUMAN
        )

        dnaRepository.create(dna)

        val exists = dnaRepository.exists(arrayOf("ABC", "DFG"))

        assertThat(exists).isTrue()
    }

    @Test
    fun `should return false when dna is not already registered`() {
        val exists = dnaRepository.exists(arrayOf("ABC", "DFG"))

        assertThat(exists).isFalse()
    }

    @Test
    fun `should return some dna dna and nothing human dna`() {
        val dna = Dna(
            dna = arrayOf("ABC", "DFG"),
            type = DnaType.SIMIAN
        )

        dnaRepository.create(dna)

        val countSimian = dnaRepository.countByType(DnaType.SIMIAN)
        val countHuman = dnaRepository.countByType(DnaType.HUMAN)

        assertThat(countSimian).isEqualTo(1)
        assertThat(countHuman).isEqualTo(0)
    }
}