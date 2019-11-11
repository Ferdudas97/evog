package org.agh.eaiib

import application.services.IntervalActionService
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CORS
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.jackson.JacksonConverter
import io.ktor.request.path
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.sessions.Sessions
import io.ktor.sessions.cookie
import io.ktor.sessions.directorySessionStorage
import io.ktor.util.KtorExperimentalAPI
import org.agh.eaiib.di.dep
import org.agh.eaiib.endpoint.*
import org.agh.eaiib.integration.session.SessionData
import org.slf4j.event.Level
import java.io.File

fun main(args: Array<String>): Unit {
    embeddedServer(Netty, port = 8080) {
        kodeinApp()
    }.start(true)
}


fun Application.kodeinApp(testing: Boolean = false) {
    module(dep(), testing)

}

@KtorExperimentalAPI
fun Application.module(kodein: Kodein, testing: Boolean = false) {
    val intervalActionService = kodein.instance<IntervalActionService>()
    intervalActionService.executeActions()
    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }

    }

    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
//        header(HttpHeaders.Authorization)
        header("MyCustomHeader")
        allowCredentials = true
        anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
    }

    install(DefaultHeaders) {
        header("X-Engine", "Ktor") // will send this header with each response

    }

    install(Sessions) {
        cookie<SessionData>("USER_ID", directorySessionStorage(File(".sessions"), cached = true)) {
            cookie.path = "/"
        }
    }


    install(ContentNegotiation) {
        register(ContentType.Application.Json, JacksonConverter(kodein.instance()))
    }


    routing {
        kodein.run {
            userRoute(instance(), instance())
            accountRoute(instance(), instance(), instance(), instance())
            eventRoute(instance(), instance(), instance(), instance(), instance(), instance(), instance())
            notificationRoute(instance(), instance(), instance(), instance())
            staticFilesRoute(instance())
        }
        get("/") {
            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
        }

        get("/json/gson") {
            call.respond(mapOf("hello" to "world"))
        }
    }
}


data class X(val x: String)


