package application.command.account.handler

import api.command.account.AccountCommand
import api.command.account.handler.AccountCommandHandler
import api.command.account.result.AccountCommandResult
import arrow.core.Either
import arrow.core.Left
import domain.account.model.Login
import domain.account.model.Password
import domain.account.repository.AccountRepository
import exceptions.DomainError
import exceptions.SavingError


class ChangePasswordHandler(private val accountRepository: AccountRepository) : AccountCommandHandler<AccountCommand.ChangePassword, AccountCommandResult.UpdatePassword>() {


    override suspend fun handle(command: AccountCommand.ChangePassword): Either<DomainError, AccountCommandResult.UpdatePassword> {
        val login = Login(command.login)
        val password = Password(command.password)
        val account = accountRepository.findByCredentials(login, password)
        return when (account) {
            null -> Left(SavingError(""))
            else -> account.copy(password = password)
                    .let { accountRepository.update(it) }
                    .map { AccountCommandResult.UpdatePassword() }

        }
    }

}