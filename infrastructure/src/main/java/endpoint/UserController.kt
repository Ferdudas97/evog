package org.agh.eaiib.endpoint

import command.user.UserCommand
import command.user.handler.UpdateUserHandler
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.routing.Route
import io.ktor.routing.post
import io.ktor.routing.route


fun Route.userRoute(updateUserHandler: UpdateUserHandler) = route("/account/user") {
    post("/update") {
        val command = call.receive<UserCommand.Update>()
        updateUserHandler.handle(command)
    }
}
