package domain.account.repository

import arrow.core.Either
import domain.account.model.Account
import domain.account.model.Login
import domain.account.model.Password
import domain.account.model.user.User
import domain.account.model.user.UserId
import exceptions.DomainError


interface UserRepository {
    suspend fun save(user: User): Either<DomainError, User>
    suspend fun findById(id: UserId): User?
    suspend fun update(user: User): Either<DomainError, User>
    suspend fun delete(userId: UserId): Either<DomainError, Unit>
}

interface AccountRepository {
    suspend fun findByCredentials(log: Login, password: Password): Account?
    suspend fun update(account: Account): Either<DomainError, Account>


}