package domain.account.repository

import arrow.core.Either
import domain.account.model.Account
import domain.account.model.Login
import domain.account.model.Password
import domain.account.model.user.User
import domain.account.model.user.UserId
import exceptions.DomainError


interface UserRepository {
    suspend fun findById(log: UserId): User?
    suspend fun update(user: User): Either<DomainError, User>
}

interface AccountRepository {
    suspend fun findByCredentials(log: Login, password: Password): Account?
    suspend fun update(account: Account): Either<DomainError, Account>
    suspend fun save(account: Account): Either<DomainError, Account>
}