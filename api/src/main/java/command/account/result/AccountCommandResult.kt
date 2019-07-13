package command.account.result

import command.CommandResult
import dto.AccountDto


sealed class AccountCommandResult : CommandResult() {
    data class Create(val accountDto: AccountDto) : AccountCommandResult()
    data class UpdatePassword(val accountDto: AccountDto) : AccountCommandResult()
    data class Login(val accountDto: AccountDto) : AccountCommandResult()
}