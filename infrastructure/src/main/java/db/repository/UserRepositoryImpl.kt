package db.repository

import account.repository.UserRepository
import account.model.user.User
import account.model.user.UserId
import account.validation.validate
import arrow.core.Either
import arrow.core.Try
import arrow.core.flatMap
import exceptions.DeleteError
import exceptions.DomainError
import exceptions.SavingError
import org.agh.eaiib.db.dao.UserDao
import org.agh.eaiib.db.mapper.UserMapper


class UserRepositoryImpl(private val userDao: UserDao) : UserRepository {

    override fun delete(userId: UserId): Either<DomainError, Unit> = Try { userDao.delete(userId.id) }
            .toEither { DeleteError(it.message ?: "Delete error") }


    override fun findById(id: UserId): User? {
        return userDao.findById(id.id)?.let(UserMapper::mapToDomain)
    }

    override fun save(user: User): Either<DomainError, User> {
        return user.validate()
                .map { UserMapper.mapToEntity(it) }
                .flatMap { Try { userDao.save(it) }.toEither { SavingError(it.message ?: "") } }
                .map { user }
    }

    override fun update(user: User): Either<DomainError, User> = user.validate()
            .map { UserMapper.mapToEntity(it) }
            .flatMap { Try { userDao.save(it) }.toEither { SavingError(it.message ?: "") } }
            .map { user }


    fun <L, R> Either<L, R>.peek(f: (R) -> Unit): Either<L, R> = when (this) {
        is Either.Left -> this
        is Either.Right -> {
            f(this.b)
            this
        }
    }


}