package command.user.handler

import arrow.core.Either
import command.user.UserCommand
import command.user.UserCommandResult
import dto.UserDtoMapper
import exceptions.DomainError
import repository.UserRepository


class UpdateUserHandler(private val userRepository: UserRepository) : UserCommandHandler<UserCommand.Update, UserCommandResult.Update>() {
    override fun handle(command: UserCommand.Update): Either<DomainError, UserCommandResult.Update> {
        return command.userDto.let { UserDtoMapper.mapToDomain(it) }
                .let { userRepository.update(it) }
                .map { UserCommandResult.Update(command.userDto) }
    }

}