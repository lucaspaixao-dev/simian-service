package com.github.lucasschwenke.simian.application

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.SerializationFeature
import com.github.lucasschwenke.simian.application.web.controllers.DnaController
import com.github.lucasschwenke.simian.application.web.controllers.StatsController
import com.github.lucasschwenke.simian.application.web.request.DnaRequest
import com.github.lucasschwenke.simian.common.exceptions.ApiException
import com.github.lucasschwenke.simian.common.koin.*
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.application.log
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.http.HttpStatusCode
import io.ktor.jackson.jackson
import io.ktor.request.httpMethod
import io.ktor.request.receive
import io.ktor.request.uri
import io.ktor.response.respond
import io.ktor.routing.post
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.routing.routing
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.inject

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(Koin) {
        modules(
            listOf(
                applicationModule,
                databaseModule,
                validatorsModule,
                dnaModule,
                statsModule
            )
        )
    }

    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)

            propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        }
    }

    install(StatusPages) {
        exception<ApiException> { cause ->
            log.error(
                "Error while processing request: ${this.context.request.httpMethod.value} - " +
                    "${this.context.request.uri}: ${cause.userResponseMessage()}. Status: ${cause.httpStatus()}"
            )

            val httpStatusCode = HttpStatusCode(cause.httpStatus(), cause.apiError().name)
            call.respond(
                status = httpStatusCode,
                message = cause.createErrorResponse()
            )
        }
    }

    val dnaController: DnaController by inject()
    val statsController: StatsController by inject()

    routing {
        route("simian") {
            post {
                this.call.receive<DnaRequest>().let {
                    call.respond(dnaController.analyze(it, this.call))
                }
            }
        }
        route("stats") {
            get {
                call.respond(statsController.getCurrentStats(this.call))
            }
        }
    }
}
