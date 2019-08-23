package api.command.account.user

import api.command.account.dto.AccountDto
import api.command.account.dto.UserDto
import command.CommandResult

sealed class UserCommandResult : CommandResult() {
    data class Update(val userDto: UserDto) : UserCommandResult()
    data class Create(val userDto: UserDto) : UserCommandResult()
    data class UpdatePassword(val accountDto: AccountDto) : UserCommandResult()
}