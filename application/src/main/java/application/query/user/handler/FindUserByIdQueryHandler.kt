package application.query.user.handler

import api.command.account.dto.UserDto
import api.query.QueryHandler
import api.query.user.UserQuery
import application.mapper.user.toDto
import domain.account.model.user.UserId
import domain.account.repository.UserRepository


class FindUserByIdQueryHandler(private val userRepository: UserRepository) : QueryHandler<UserQuery.FindById, UserDto?> {
    override suspend fun exevute(query: UserQuery.FindById): UserDto? {
        return userRepository.findById(UserId(query.id))?.toDto()
    }
}