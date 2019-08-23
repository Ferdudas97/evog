package db.repository.account

import arrow.core.Either
import arrow.core.Try
import db.dao.account.UserDao
import domain.account.model.Account
import domain.account.model.Login
import domain.account.model.Password
import domain.account.repository.AccountRepository
import domain.account.validation.validateAccount
import exceptions.DomainError
import exceptions.UpdateError
import org.agh.eaiib.db.mapper.AccountMapper
import org.agh.eaiib.db.mapper.UserMapper


class AccountRepositoryImpl(private val userDao: UserDao) : AccountRepository {
    override suspend fun update(account: Account): Either<DomainError, Account> {
        val validated = account.validateAccount()
        return when (validated) {

            is Either.Left -> validated
            is Either.Right -> Try { userDao.updateAccount(validated.b.let(AccountMapper::mapToEntity)) }
                    .toEither()
                    .map { account }
                    .mapLeft { UpdateError(it.message ?: "") }

        }
    }

    override suspend fun findByCredentials(log: Login, password: Password): Account? {
        return userDao.findByCredentials(log.value, password.value)
                ?.let(UserMapper::mapToDomain)
                ?.account
    }



}