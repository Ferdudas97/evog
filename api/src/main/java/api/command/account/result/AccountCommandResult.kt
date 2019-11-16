package api.command.account.result

import api.command.CommandResult
import api.command.account.dto.AccountDto


sealed class AccountCommandResult : CommandResult() {
    class Create : AccountCommandResult()
    class UpdatePassword : AccountCommandResult()
    class Login(val accountDto: AccountDto) : AccountCommandResult()
}