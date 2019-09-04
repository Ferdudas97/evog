package application.command.account.handler

import api.command.account.AccountCommand
import api.command.account.handler.AccountCommandHandler
import api.command.account.result.AccountCommandResult
import application.mapper.user.toDomain
import application.mapper.user.toDto
import arrow.core.Either
import domain.account.repository.AccountRepository
import exceptions.DomainError
import integration.DomainEvent


class CreateUserCommandHandler(private val accountRepository: AccountRepository,
                               private val eventSender: (DomainEvent) -> Unit) : AccountCommandHandler<AccountCommand.Create, AccountCommandResult.Create>() {


    override suspend fun handle(command: AccountCommand.Create): Either<DomainError, AccountCommandResult.Create> {
        val account = command.account.toDomain()
        return accountRepository.save(account)
                .map { it.toDto() }
                .map { AccountCommandResult.Create(it) }

    }

}