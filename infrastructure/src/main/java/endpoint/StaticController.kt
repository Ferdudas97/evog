package org.agh.eaiib.endpoint

import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.accept
import io.ktor.routing.get
import io.ktor.routing.route
import java.io.File

const val imagsPath = "C:\\Users\\radek\\IdeaProjects\\evog\\infrastructure\\src\\main\\resources\\images"
fun Route.staticFilesRoute() = route("/images") {
    get {
        val names = File(imagsPath).listFiles()
                .map { it.name }
        call.respond(names)
    }
    accept(ContentType.Image.Any) {
        static {
            resources("images")
        }
    }
}