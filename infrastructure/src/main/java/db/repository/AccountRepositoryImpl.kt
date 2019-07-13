package org.agh.eaiib.db.repository

import account.Account
import account.Login
import account.Password
import account.validation.validateAccount
import arrow.core.Either
import arrow.core.Try
import exceptions.DomainError
import exceptions.SavingError
import exceptions.UpdateError
import org.agh.eaiib.db.dao.AccountDao
import org.agh.eaiib.db.mapper.AccountMapper
import repository.AccountRepository


class AccountRepositoryImpl(private val accountDao: AccountDao) : AccountRepository {
    override fun update(account: Account): Either<DomainError, Account> {
        val validated = account.validateAccount()
        return when (validated) {

            is Either.Left -> validated
            is Either.Right -> Try { accountDao.update(validated.b.let(AccountMapper::mapToEntity)) }
                    .toEither()
                    .map { account }
                    .mapLeft { UpdateError(it.message ?: "") }

        }
    }

    override fun findByCredentials(log: Login, password: Password): Account? {
        return accountDao.findByCredentials(log.value, password.value)
                ?.let(AccountMapper::mapToDomain)
    }


    override fun save(account: Account): Either<DomainError, Account> {
        val validated = account.validateAccount()
        return when (validated) {

            is Either.Left -> validated
            is Either.Right -> Try { accountDao.save(validated.b.let(AccountMapper::mapToEntity)) }
                    .toEither()
                    .map { account }
                    .mapLeft { SavingError(it.message ?: "") }

        }


    }


}