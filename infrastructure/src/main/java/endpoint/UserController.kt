package org.agh.eaiib.endpoint

import api.command.account.dto.UserDto
import api.command.account.user.UserCommand
import api.query.user.UserQuery
import application.command.account.user.handler.UpdateUserHandler
import application.query.user.handler.FindUserByIdQueryHandler
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.put
import io.ktor.routing.route


fun Route.userRoute(updateUserHandler: UpdateUserHandler,
                    findUserByIdQueryHandler: FindUserByIdQueryHandler) = route("/users") {

    put("/{id}") {
        val id = call.parameters["id"]
        val userDto = call.receive<UserDto>()
        val command = UserCommand.Update(userDto)
        updateUserHandler.handle(command)
    }

    get("/{id}") {
        val id = call.parameters["id"]
        id?.let {
            val query = UserQuery.FindById(it)
            val user = findUserByIdQueryHandler.exevute(query)
            if (user != null) {
                call.respond(user)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }
    }

}
