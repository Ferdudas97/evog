package api.command.account

import api.command.account.dto.AccountDto
import command.Command


sealed class AccountCommand : Command() {
    data class ChangePassword(val login: String, val password: String) : AccountCommand()
    data class Create(val account: AccountDto) : AccountCommand()
    data class Login(val login: String, val password: String) : AccountCommand()
}