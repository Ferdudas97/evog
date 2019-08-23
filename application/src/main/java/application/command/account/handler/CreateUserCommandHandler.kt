package application.command.account.handler

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


class CreateUserCommandHandler(private val userRepository: UserRepository, private val eventSender: (DomainEvent) -> Unit)
    : UserCommandHandler<UserCommand.Create, UserCommandResult.Create>() {


    override suspend fun handle(command: UserCommand.Create): Either<DomainError, UserCommandResult.Create> {
        val user = command.userDto.let { UserDtoMapper.mapToDomain(it) }
        return userRepository.save(user)
                .map { UserDtoMapper.mapToDto(it) }
                .peek { dto -> eventSender.apply { UserEvent.Created(dto) } }
                .map { UserCommandResult.Create(it) }

    }

}