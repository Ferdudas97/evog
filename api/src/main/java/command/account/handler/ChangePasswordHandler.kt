package command.account.handler

import account.Login
import account.Password
import arrow.core.Either
import arrow.core.Left
import command.account.AccountCommand
import command.account.AccountCommandHandler
import command.account.result.AccountCommandResult
import dto.AccountDtoMapper
import exceptions.DomainError
import exceptions.SavingError
import repository.AccountRepository


class ChangePasswordHandler(val accountRepository: AccountRepository) : AccountCommandHandler<AccountCommand.ChangePassword, AccountCommandResult.UpdatePassword>() {


    override fun handle(command: AccountCommand.ChangePassword): Either<DomainError, AccountCommandResult.UpdatePassword> {
        val login = Login(command.login)
        val password = Password(command.password)
        val account = accountRepository.findByCredentials(login, password)
        return when (account) {
            null -> Left(SavingError(""))
            else -> account.copy(password = password)
                    .let { accountRepository.update(it) }
                    .map { AccountDtoMapper.mapToDto(it) }
                    .map { AccountCommandResult.UpdatePassword(it) }

        }
    }

}