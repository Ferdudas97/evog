package application.command.account.handler

import api.command.command.account.AccountCommand
import api.command.command.account.event.AccountEvent
import api.command.command.account.handler.AccountCommandHandler
import api.command.command.account.result.AccountCommandResult
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


class ChangePasswordHandler(private val accountRepository: AccountRepository, private val eventSender: (DomainEvent) -> Unit) : AccountCommandHandler<AccountCommand.ChangePassword, AccountCommandResult.UpdatePassword>() {


    override fun handle(command: AccountCommand.ChangePassword): Either<DomainError, AccountCommandResult.UpdatePassword> {
        val login = Login(command.login)
        val password = Password(command.password)
        val account = accountRepository.findByCredentials(login, password)
        return when (account) {
            null -> Left(SavingError(""))
            else -> account.copy(password = password)
                    .let { accountRepository.update(it) }
                    .map { AccountDtoMapper.mapToDto(it) }
                    .peek { eventSender.apply { AccountEvent.PasswordUpdated(command.login, command.password) } }
                    .map { AccountCommandResult.UpdatePassword(it) }

        }
    }

}