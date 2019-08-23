package application.command.account.handler

import api.command.account.event.UserEvent
import api.command.account.user.UserCommand
import api.command.account.user.UserCommandResult
import api.command.account.user.handler.UserCommandHandler
import application.command.account.AccountDtoMapper
import arrow.core.Either
import arrow.core.Left
import arrow.peek
import domain.account.model.Login
import domain.account.model.Password
import domain.account.repository.AccountRepository
import exceptions.DomainError
import exceptions.SavingError
import integration.DomainEvent


class ChangePasswordHandler(private val accountRepository: AccountRepository, private val eventSender: (DomainEvent) -> Unit) : UserCommandHandler<UserCommand.ChangePassword, UserCommandResult.UpdatePassword>() {


    override suspend fun handle(command: UserCommand.ChangePassword): Either<DomainError, UserCommandResult.UpdatePassword> {
        val login = Login(command.login)
        val password = Password(command.password)
        val account = accountRepository.findByCredentials(login, password)
        return when (account) {
            null -> Left(SavingError(""))
            else -> account.copy(password = password)
                    .let { accountRepository.update(it) }
                    .map { AccountDtoMapper.mapToDto(it) }
                    .peek { eventSender.apply { UserEvent.PasswordUpdated(command.login, command.password) } }
                    .map { UserCommandResult.UpdatePassword(it) }

        }
    }

}