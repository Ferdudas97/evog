package command

import account.Account
import account.Login
import account.Password


sealed class AccountCommand : Command() {
    data class CreateAccountCommand(val account: Account)
    data class ChangePasswordCommand(val login: String, val password: String)
    data class GetAccountCommand(val login: String, val password: String)
}