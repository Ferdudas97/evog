package command.account.handler

import arrow.core.Either
import command.account.AccountCommand
import command.account.result.AccountCommandResult
import command.handler.DomainCommandHandler
import dto.AccountDtoMapper
import exceptions.DomainError
import repository.AccountRepository


class CreateAccountHandler(val accountRepository: AccountRepository) : DomainCommandHandler<AccountCommand.Create, AccountCommandResult.Create>() {


    override fun handle(command: AccountCommand.Create): Either<DomainError, AccountCommandResult.Create> {
        val account = command.account.let { AccountDtoMapper.mapToDomain(it) }
        return accountRepository.save(account)
                .map { AccountDtoMapper.mapToDto(it) }
                .map { AccountCommandResult.Create(it) }

    }

}