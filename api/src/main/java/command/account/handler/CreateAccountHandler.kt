package command.account.handler

import arrow.core.Either
import arrow.peek
import command.account.AccountCommand
import command.account.result.AccountCommandResult
import dto.AccountDtoMapper
import event.AccountEvent
import exceptions.DomainError
import integration.DomainEvent
import repository.AccountRepository


class CreateAccountHandler(private val accountRepository: AccountRepository, private val eventSender: (DomainEvent) -> Unit) : AccountCommandHandler<AccountCommand.Create, AccountCommandResult.Create>() {


    override fun handle(command: AccountCommand.Create): Either<DomainError, AccountCommandResult.Create> {
        val account = command.account.let { AccountDtoMapper.mapToDomain(it) }
        return accountRepository.save(account)
                .map { AccountDtoMapper.mapToDto(it) }
                .peek { dto -> eventSender.apply { AccountEvent.Created(dto) } }
                .map { AccountCommandResult.Create(it) }

    }

}