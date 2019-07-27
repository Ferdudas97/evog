package api.command.command.account

import api.command.command.account.dto.AccountDto
import command.Command


sealed class AccountCommand : Command() {
    data class Create(val account: AccountDto) : AccountCommand()
    data class ChangePassword(val login: String, val password: String) : AccountCommand()
}