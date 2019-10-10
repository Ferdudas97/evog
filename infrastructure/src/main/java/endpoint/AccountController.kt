package org.agh.eaiib.endpoint

import api.command.account.AccountCommand
import api.command.account.dto.AccountDto
import api.command.account.dto.CredentialsDto
import application.command.account.handler.ChangePasswordHandler
import application.command.account.handler.CreateUserCommandHandler
import application.command.account.handler.LoginCommandHandler
import arrow.core.Either
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post
import io.ktor.routing.put
import io.ktor.routing.route
import io.ktor.sessions.Sessions
import io.ktor.sessions.sessions
import io.ktor.sessions.set
import org.agh.eaiib.integration.session.SessionData


fun Route.accountRoute(changePasswordHandler: ChangePasswordHandler,
                       loginCommandHandler: LoginCommandHandler,
                       createUserCommandHandler: CreateUserCommandHandler) = route("/accounts") {
    post {
        val accountDto = call.receive<AccountDto>()
        val command = AccountCommand.Create(accountDto)
        val result = createUserCommandHandler.handle(command)
        when (result) {
            is Either.Right -> call.respond(result.b.accountDto)
            is Either.Left -> call.respond(HttpStatusCode.NotFound, result.a)
        }
    }


    post("/login") {
        val credentials = call.receive<CredentialsDto>()
        val command = AccountCommand.Login(credentials.login, credentials.password)
        val result = loginCommandHandler.handle(command)
        when (result) {
            is Either.Right -> {
                call.apply {
                    sessions.set(SessionData(result.b.accountDto.user.id!!))
                    respond(result.b.accountDto)
                }
            }
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
