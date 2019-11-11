package org.agh.eaiib.endpoint

import api.command.account.AccountCommand
import api.command.account.dto.AccountDto
import api.command.account.dto.CredentialsDto
import application.command.account.handler.ChangePasswordHandler
import application.command.account.handler.CreateUserCommandHandler
import application.command.account.handler.LoginCommandHandler
import arrow.core.Either
import com.fasterxml.jackson.databind.ObjectMapper
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.PartData
import io.ktor.http.content.forEachPart
import io.ktor.request.receive
import io.ktor.request.receiveMultipart
import io.ktor.response.respond
import io.ktor.routing.*
import io.ktor.sessions.sessions
import io.ktor.sessions.set
import kotlinx.io.core.readBytes
import org.agh.eaiib.integration.session.SessionData


fun Route.accountRoute(changePasswordHandler: ChangePasswordHandler,
                       loginCommandHandler: LoginCommandHandler,
                       createUserCommandHandler: CreateUserCommandHandler,
                       objectMapper: ObjectMapper) = route("/accounts") {
    accept(ContentType.MultiPart.Any) {
        post {
            val parts = call.receiveMultipart()
            val bytes = mutableListOf<Any>()
            parts.forEachPart {
                bytes.add(when (it) {
                    is PartData.FileItem -> it.provider().readBytes()
                    is PartData.BinaryItem -> it.provider().readBytes()
                    is PartData.FormItem -> objectMapper.readValue(it.value, AccountDto::class.java)
                })

            }
            val accountDto = bytes.filterIsInstance<AccountDto>().first()
            val command = AccountCommand.Create(accountDto, bytes.filterIsInstance<ByteArray>())
            val result = createUserCommandHandler.handle(command)
            when (result) {
                is Either.Right -> call.respond(result.b
                )
                is Either.Left -> call.respond(HttpStatusCode.NotFound, result.a)
            }
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
