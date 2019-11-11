package api.command.account.result

import api.command.account.dto.AccountDto
import command.CommandResult


sealed class AccountCommandResult : CommandResult() {
    class Create : AccountCommandResult()
    class UpdatePassword : AccountCommandResult()
    class Login(val accountDto: AccountDto) : AccountCommandResult()
}