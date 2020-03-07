package com.github.lucasschwenke.simian.resource.repository.dna

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.lucasschwenke.simian.domain.dna.Dna
import com.github.lucasschwenke.simian.domain.dna.repositories.DnaRepository
import com.github.lucasschwenke.simian.resource.repository.dna.entities.DnaEntity
import com.mongodb.BasicDBObject
import com.mongodb.MongoClient
import com.mongodb.client.MongoCollection
import io.azam.ulidj.ULID
import org.bson.Document as BsonDocument

class DnaRepositoryDb(
    client: MongoClient,
    database: String,
    private val objectMapper: ObjectMapper
) : DnaRepository {
    private val collection: MongoCollection<BsonDocument>

    companion object {
        const val DNA = "dna"
    }

    init {
        val db = client.getDatabase(database)
        collection = db.getCollection(DNA)
    }

    override fun create(dna: Dna) {
        val dnaEntity = DnaEntity(
            id = ULID.random().toString(),
            dna = dna.dna,
            type = dna.type
        )

        val json = objectMapper.writeValueAsString(dnaEntity)
        collection.insertOne(BsonDocument.parse(json))
    }

    override fun exists(dna: Array<String>) : Boolean {
        val searchParams = mutableMapOf(DNA to dna)
        val search = BasicDBObject(searchParams.toMap())

        return collection.find(search).firstOrNull() != null
    }

    override fun findAll() : List<Dna> =
        collection.find()
            .map { objectMapper.readValue<DnaEntity>(it.toJson()) }
            .map { Dna(it.dna, it.type) }
            .toList()
}