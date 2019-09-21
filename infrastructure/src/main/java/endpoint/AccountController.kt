package org.agh.eaiib.endpoint

import api.command.account.AccountCommand
import api.command.account.dto.AccountDto
import application.command.account.handler.ChangePasswordHandler
import application.command.account.handler.CreateUserCommandHandler
import arrow.core.Either
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post
import io.ktor.routing.put
import io.ktor.routing.route


fun Route.accountRoute(changePasswordHandler: ChangePasswordHandler,
                       createUserCommandHandler: CreateUserCommandHandler) = route("/accounts") {
    post("") {
        val accountDto = call.receive<AccountDto>()
        val command = AccountCommand.Create(accountDto)
        val result = createUserCommandHandler.handle(command)
        when (result) {
            is Either.Right -> call.respond(result.b.accountDto)
            is Either.Left -> call.respond(HttpStatusCode.NotFound, result.a)
        }
    }



    put("/{login}") {
        val login = call.parameters["login"]
        val accountDto = call.receive<AccountDto>()
        val command = AccountCommand.ChangePassword(accountDto.credentials.login, accountDto.credentials.password)
        changePasswordHandler.handle(command)
    }
}
