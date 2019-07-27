package api.command.command.account.user

import api.command.command.account.dto.UserDto
import command.CommandResult

sealed class UserCommandResult : CommandResult() {
    data class Update(val userDto: UserDto) : UserCommandResult()
}