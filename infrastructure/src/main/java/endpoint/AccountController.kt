package org.agh.eaiib.endpoint

import api.command.account.AccountCommand
import api.command.account.dto.AccountDto
import application.command.account.handler.ChangePasswordHandler
import application.command.account.handler.CreateUserCommandHandler
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.routing.Route
import io.ktor.routing.post
import io.ktor.routing.put
import io.ktor.routing.route


fun Route.accountRoute(changePasswordHandler: ChangePasswordHandler,
                       createUserCommandHandler: CreateUserCommandHandler) = route("/accounts") {
    post("") {
        val accountDto = call.receive<AccountDto>()
        val command = AccountCommand.Create(accountDto)
        createUserCommandHandler.handle(command)
    }

    put("/{login}") {
        val login = call.parameters["logim"]
        val accountDto = call.receive<AccountDto>()
        val command = AccountCommand.ChangePassword(accountDto.login, accountDto.password)
        changePasswordHandler.handle(command)
    }
}
