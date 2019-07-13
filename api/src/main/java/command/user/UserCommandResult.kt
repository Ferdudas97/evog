package command.user

import command.CommandResult
import dto.UserDto

sealed class UserCommandResult : CommandResult() {
    data class Update(val userDto: UserDto) : UserCommandResult()
}