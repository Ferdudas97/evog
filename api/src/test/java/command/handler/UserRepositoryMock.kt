package command.handler

import account.repository.UserRepository
import account.model.user.User
import account.model.user.UserId
import arrow.core.Either
import arrow.core.Right
import arrow.core.left
import arrow.core.right
import exceptions.DomainError
import exceptions.UpdateError


class UserRepositoryMock : UserRepository {


    val db = mutableMapOf<UserId, User>()


    override fun save(user: User): Either<DomainError, User> {
        db[user.id] = user
        return Right(user)
    }

    override fun findById(id: UserId): User? {
        return db[id]
    }

    override fun update(user: User): Either<DomainError, User> {
        return db[user.id]?.right() ?: UpdateError("error").left()
    }

    override fun delete(userId: UserId): Either<DomainError, Unit> {
        db.remove(userId)
        return Unit.right()
    }

}