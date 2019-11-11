package application.command.account.handler

import api.command.account.AccountCommand
import api.command.account.handler.AccountCommandHandler
import api.command.account.result.AccountCommandResult
import application.mapper.user.toDomain
import arrow.core.Either
import common.ImageRepository
import domain.account.repository.AccountRepository
import exceptions.DomainError


class CreateUserCommandHandler(private val accountRepository: AccountRepository,
                               private val imageRepository: ImageRepository) : AccountCommandHandler<AccountCommand.Create, AccountCommandResult.Create>() {


    override suspend fun handle(command: AccountCommand.Create): Either<DomainError, AccountCommandResult.Create> {
        val accountDto = command.account
        val filesId = command.photos.map { imageRepository.save(it) }
        val accountWithFiles = accountDto.copy(user = accountDto.user.copy(photosId = filesId))
        return accountRepository.save(account = accountWithFiles.toDomain())
                .map { AccountCommandResult.Create() }

    }

}