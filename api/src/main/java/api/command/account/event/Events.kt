package api.command.account.event

import api.command.account.dto.UserDto
import integration.DomainEvent

sealed class UserEvent : DomainEvent {
    data class Created(val userDto: UserDto)
    data class PasswordUpdated(val login: String, val password: String)
    data class Updated(val userDto: UserDto) : UserEvent()
}
