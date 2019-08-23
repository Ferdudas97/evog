package db.repository

import arrow.core.Either
import arrow.core.Try
import arrow.core.flatMap
import db.dao.account.UserDao
import domain.account.model.user.User
import domain.account.model.user.UserId
import domain.account.repository.UserRepository
import domain.account.validation.validate
import exceptions.DeleteError
import exceptions.DomainError
import exceptions.SavingError
import org.agh.eaiib.db.mapper.UserMapper


class UserRepositoryImpl(private val userDao: UserDao) : UserRepository {

    override suspend fun delete(userId: UserId): Either<DomainError, Unit> = Try { userDao.delete(userId.id) }
            .toEither { DeleteError(it.message ?: "Delete error") }


    override suspend fun findById(id: UserId): User? {
        return userDao.findById(id.id)?.let(UserMapper::mapToDomain)
    }

    override suspend fun save(user: User): Either<DomainError, User> {
        return user.validate()
                .map { UserMapper.mapToEntity(it) }
                .flatMap { Try { userDao.save(it) }.toEither { SavingError(it.message ?: "") } }
                .map { user }
    }

    override suspend fun update(user: User): Either<DomainError, User> = user.validate()
            .map { UserMapper.mapToEntity(it) }
            .flatMap { Try { userDao.save(it) }.toEither { SavingError(it.message ?: "") } }
            .map { user }


}