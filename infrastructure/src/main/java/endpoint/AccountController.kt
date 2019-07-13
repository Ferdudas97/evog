package org.agh.eaiib.endpoint

import command.account.AccountCommand
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.routing.Route
import io.ktor.routing.post
import io.ktor.routing.route
import repository.AccountRepository


class AccountController(val accountRepository: AccountRepository) {


    fun Route.accountRoutes() = route("/accounts") {
        post("/") {
            call.receive<AccountCommand.Create>()

        }
    }
}