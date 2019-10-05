package db.repository

import arrow.core.Either
import arrow.core.Try
import arrow.core.flatMap
import db.dao.account.AccountDao
import db.mapper.user.toDomain
import db.mapper.user.toEntity
import domain.account.model.user.User
import domain.account.model.user.UserId
import domain.account.repository.UserRepository
import domain.account.validation.validate
import exceptions.DomainError
import exceptions.SavingError


class UserRepositoryImpl(private val accountDao: AccountDao) : UserRepository {


    override suspend fun findById(log: UserId): User? {
        return accountDao.findUserById(log.id)?.toDomain()
    }


    override suspend fun update(user: User): Either<DomainError, User> = user.validate()
            .map { it.toEntity() }
            .flatMap { Try { accountDao.updateUser(it) }.toEither { SavingError(it.message ?: "") } }
            .map { user }


}