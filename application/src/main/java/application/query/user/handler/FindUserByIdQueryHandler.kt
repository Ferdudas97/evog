package application.query.user.handler

import api.command.account.dto.UserDto
import api.query.user.UserQuery
import application.command.account.toDto
import domain.account.model.user.UserId
import domain.account.repository.UserRepository
import query.QueryHandler


class FindUserByIdQueryHandler(private val userRepository: UserRepository) : QueryHandler<UserQuery.FindById, UserDto?> {
    override suspend fun exevute(query: UserQuery.FindById): UserDto? {
        return userRepository.findById(UserId(query.id))?.toDto()

    }
}