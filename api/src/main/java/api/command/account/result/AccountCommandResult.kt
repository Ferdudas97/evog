package api.command.account.result

import api.command.account.dto.AccountDto
import command.CommandResult


sealed class AccountCommandResult : CommandResult() {
    data class Create(val accountDto: AccountDto) : AccountCommandResult()
    data class UpdatePassword(val accountDto: AccountDto) : AccountCommandResult()
    data class Login(val accountDto: AccountDto) : AccountCommandResult()
}