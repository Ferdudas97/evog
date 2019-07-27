package application.command.account.handler

import api.command.command.account.AccountCommand
import api.command.command.account.event.AccountEvent
import api.command.command.account.handler.AccountCommandHandler
import api.command.command.account.result.AccountCommandResult
import application.command.account.AccountDtoMapper
import arrow.core.Either
import arrow.peek
import domain.account.repository.AccountRepository
import exceptions.DomainError
import integration.DomainEvent


class CreateAccountHandler(private val accountRepository: AccountRepository, private val eventSender: (DomainEvent) -> Unit) : AccountCommandHandler<AccountCommand.Create, AccountCommandResult.Create>() {


    override fun handle(command: AccountCommand.Create): Either<DomainError, AccountCommandResult.Create> {
        val account = command.account.let { AccountDtoMapper.mapToDomain(it) }
        return accountRepository.save(account)
                .map { AccountDtoMapper.mapToDto(it) }
                .peek { dto -> eventSender.apply { AccountEvent.Created(dto) } }
                .map { AccountCommandResult.Create(it) }

    }

}