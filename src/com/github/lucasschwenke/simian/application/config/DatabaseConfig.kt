package com.github.lucasschwenke.simian.application.config

import com.mongodb.MongoClient
import com.mongodb.MongoClientOptions
import com.mongodb.MongoCredential
import com.mongodb.ServerAddress
import com.typesafe.config.ConfigFactory
import io.ktor.config.HoconApplicationConfig
import io.ktor.util.KtorExperimentalAPI

@KtorExperimentalAPI
object DatabaseConfig {

    private val appConfig = HoconApplicationConfig(ConfigFactory.load())

    fun connect(): MongoClient {
        val serverAddress = ServerAddress(appConfig.property("db.mongoHost").getString())

        val credential = MongoCredential.createCredential(
            appConfig.property("db.mongoAuthUser").getString(),
            appConfig.property("db.mongoDatabase").getString(),
            appConfig.property("db.mongoAuthPassword").getString().toCharArray()
        )

        val options = MongoClientOptions.builder().build()

        return MongoClient(serverAddress, credential, options)
    }

    fun getDatabase() = appConfig.property("db.mongoDatabase").getString()
}