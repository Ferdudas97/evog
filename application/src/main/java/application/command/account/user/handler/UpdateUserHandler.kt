package application.command.account.user.handler

import api.command.account.event.UserEvent
import api.command.account.user.UserCommand
import api.command.account.user.UserCommandResult
import api.command.account.user.handler.UserCommandHandler
import application.command.account.UserDtoMapper
import arrow.core.Either
import arrow.peek
import domain.account.repository.UserRepository
import exceptions.DomainError
import integration.DomainEvent


class UpdateUserHandler(private val userRepository: UserRepository, private val eventSender: (DomainEvent) -> Unit) : UserCommandHandler<UserCommand.Update, UserCommandResult.Update>() {
    override suspend fun handle(command: UserCommand.Update): Either<DomainError, UserCommandResult.Update> {
        return command.userDto.let { UserDtoMapper.mapToDomain(it) }
                .let { userRepository.update(it) }
                .map { command.userDto }
                .peek { eventSender.apply { UserEvent.Updated(it) } }
                .map { UserCommandResult.Update(command.userDto) }
    }

}