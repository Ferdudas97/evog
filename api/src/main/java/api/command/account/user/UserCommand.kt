package api.command.account.user

import api.command.Command
import api.command.account.dto.UserDto

sealed class UserCommand : Command() {
    data class Update(val userDto: UserDto) : UserCommand()
}