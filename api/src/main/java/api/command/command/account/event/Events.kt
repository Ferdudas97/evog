package api.command.command.account.event

import api.command.command.account.dto.AccountDto
import api.command.command.account.dto.UserDto
import integration.DomainEvent

sealed class UserEvent : DomainEvent {
    data class Updated(val userDto: UserDto) : UserEvent()
}

sealed class AccountEvent : DomainEvent {
    data class Created(val accountDto: AccountDto)
    data class PasswordUpdated(val login: String, val password: String)
}

