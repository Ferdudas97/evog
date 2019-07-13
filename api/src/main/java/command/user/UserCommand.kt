package command.user

import command.Command
import dto.UserDto

sealed class UserCommand : Command() {
    data class Update(val userDto: UserDto) : UserCommand()
}