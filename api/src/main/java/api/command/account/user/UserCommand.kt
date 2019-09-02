package api.command.account.user

import api.command.account.dto.UserDto
import command.Command

sealed class UserCommand : Command() {
    data class Update(val userDto: UserDto) : UserCommand()
}