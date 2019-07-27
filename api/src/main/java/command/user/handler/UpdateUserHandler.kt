package command.user.handler

import account.repository.UserRepository
import arrow.core.Either
import arrow.peek
import command.user.UserCommand
import command.user.UserCommandResult
import dto.UserDtoMapper
import event.UserEvent
import exceptions.DomainError
import integration.DomainEvent


class UpdateUserHandler(private val userRepository: UserRepository, private val eventSender: (DomainEvent) -> Unit) : UserCommandHandler<UserCommand.Update, UserCommandResult.Update>() {
    override fun handle(command: UserCommand.Update): Either<DomainError, UserCommandResult.Update> {
        return command.userDto.let { UserDtoMapper.mapToDomain(it) }
                .let { userRepository.update(it) }
                .map { command.userDto }
                .peek { eventSender.apply { UserEvent.Updated(it) } }
                .map { UserCommandResult.Update(command.userDto) }
    }

}