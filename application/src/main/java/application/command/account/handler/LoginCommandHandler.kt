package application.command.account.handler

import api.command.account.AccountCommand
import api.command.account.handler.AccountCommandHandler
import api.command.account.result.AccountCommandResult
import application.mapper.user.toDto
import arrow.core.Either
import arrow.core.left
import arrow.core.right
import domain.account.model.Login
import domain.account.model.Password
import domain.account.repository.AccountRepository
import exceptions.DomainError
import exceptions.ItemNotFoundError

class LoginCommandHandler(private val accountRepository: AccountRepository)
    : AccountCommandHandler<AccountCommand.Login, AccountCommandResult.Login>() {

    override suspend fun handle(command: AccountCommand.Login): Either<DomainError, AccountCommandResult.Login> {
        val account = accountRepository.findByCredentials(Login(command.login), Password(command.password))
        return when (account) {
            null -> ItemNotFoundError("cannot find account with login ${command.login}").left()
            else -> AccountCommandResult.Login(account.toDto()).right()
        }
    }
}