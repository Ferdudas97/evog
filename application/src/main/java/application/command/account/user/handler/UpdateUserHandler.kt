package application.command.account.user.handler

import api.command.account.user.UserCommand
import api.command.account.user.UserCommandResult
import api.command.account.user.handler.UserCommandHandler
import application.mapper.user.toDomain
import arrow.core.Either
import domain.account.repository.UserRepository
import exceptions.DomainError


class UpdateUserHandler(private val userRepository: UserRepository) : UserCommandHandler<UserCommand.Update, UserCommandResult.Update>() {
    override suspend fun handle(command: UserCommand.Update): Either<DomainError, UserCommandResult.Update> {
        return command.userDto.toDomain()
                .let { userRepository.update(it) }
                .map { command.userDto }
                .map { UserCommandResult.Update(command.userDto) }
    }

}