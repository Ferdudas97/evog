package org.agh.eaiib.endpoint

import api.command.command.account.AccountCommand
import application.command.account.handler.ChangePasswordHandler
import application.command.account.handler.CreateAccountHandler
import com.google.gson.Gson
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route


fun Route.accountRoutes(createAccountHandler: CreateAccountHandler,
                        changePasswordHandler: ChangePasswordHandler) = route("/accounts") {
    post("/create") {
        val command = call.receive<AccountCommand.Create>()
        createAccountHandler.handle(command)
                .map {
                    call.respond(Gson().toJson(it))
                }

    }

    post("/update") {
        val command = call.receive<AccountCommand.ChangePassword>()
        changePasswordHandler.handle(command)
    }
    get("") {
        call.respond(mapOf("hello" to "world"))

    }

}

