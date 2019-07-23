package org.agh.eaiib.endpoint

import com.google.gson.Gson
import command.account.AccountCommand
import command.account.handler.ChangePasswordHandler
import command.account.handler.CreateAccountHandler
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

