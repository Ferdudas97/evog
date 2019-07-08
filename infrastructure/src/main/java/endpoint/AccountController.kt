package org.agh.eaiib.endpoint

import io.ktor.application.call
import io.ktor.routing.Route
import io.ktor.routing.post
import io.ktor.routing.route


class AccountController {

    fun Route.accountRoutes() = route("/accounts") {
        post("/") {
            call.request
        }
    }
}