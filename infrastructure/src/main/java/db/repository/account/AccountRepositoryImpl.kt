package db.repository.account

import arrow.core.Either
import arrow.core.Try
import arrow.core.flatMap
import db.dao.account.AccountDao
import db.mapper.user.toDomain
import db.mapper.user.toEntity
import domain.account.model.Account
import domain.account.model.Login
import domain.account.model.Password
import domain.account.repository.AccountRepository
import domain.account.validation.validateAccount
import exceptions.DomainError
import exceptions.SavingError
import exceptions.UpdateError


class AccountRepositoryImpl(private val userDao: AccountDao) : AccountRepository {
    override suspend fun update(account: Account): Either<DomainError, Account> {
        val validated = account.validateAccount()
        return when (validated) {

            is Either.Left -> validated
            is Either.Right -> Try { userDao.update(validated.b.toEntity()) }
                    .toEither()
                    .map { account }
                    .mapLeft { UpdateError(it.message ?: "") }

        }
    }

    override suspend fun findByCredentials(log: Login, password: Password): Account? {
        return userDao.findByCredentials(log.value, password.value)?.toDomain()
    }

    override suspend fun save(account: Account): Either<DomainError, Account> {
        return account.validateAccount()
                .flatMap {
                    Try {
                        userDao.save(account.toEntity())
                    }.toEither {
                        SavingError(it.message ?: "")
                    }
                }
                .map { account }
    }
}