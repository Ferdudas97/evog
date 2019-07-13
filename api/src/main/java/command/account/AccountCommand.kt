package command.account

import command.Command
import dto.AccountDto


sealed class AccountCommand : Command() {
    data class Create(val account: AccountDto) : AccountCommand()
    data class ChangePassword(val login: String, val password: String) : AccountCommand()
}