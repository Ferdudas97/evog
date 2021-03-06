package api.command.account

import api.command.Command
import api.command.account.dto.AccountDto


sealed class AccountCommand : Command() {
    data class ChangePassword(val login: String, val password: String) : AccountCommand()
    data class Create(val account: AccountDto, val photos : List<ByteArray> = emptyList() ) : AccountCommand()
    data class Login(val login: String, val password: String) : AccountCommand()
}