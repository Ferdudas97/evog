package application.command.account

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import domain.account.model.user.User
import domain.account.model.user.UserId
import domain.account.repository.UserRepository
import exceptions.DomainError
import exceptions.UpdateError


class UserRepositoryMock : UserRepository {

    private val db = mutableMapOf<UserId, User>()

    override suspend fun findById(log: UserId): User? {
        return db[log]
    }

    override suspend fun update(user: User): Either<DomainError, User> {
        db[user.id] = user
        return db[user.id]?.right() ?: UpdateError("error").left()
    }

    fun save(user: User) {
        db[user.id] = user
    }


}