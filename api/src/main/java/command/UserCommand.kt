package command

sealed class UserCommand : Command() {
    data class CreateUserCommand(val id: String)
    data class DeleteUserCommand(val id: String)
    data class UpdateUserCommand(val id: String)
}