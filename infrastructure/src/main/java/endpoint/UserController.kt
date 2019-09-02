package org.agh.eaiib.endpoint

import api.command.account.dto.UserDto
import api.command.account.user.UserCommand
import application.command.account.user.handler.UpdateUserHandler
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.routing.Route
import io.ktor.routing.put
import io.ktor.routing.route


fun Route.userRoute(updateUserHandler: UpdateUserHandler) = route("/users") {

    put("/{id}") {
        val id = call.parameters["id"]
        val userDto = call.receive<UserDto>()
        val command = UserCommand.Update(userDto)
        updateUserHandler.handle(command)
    }

}
