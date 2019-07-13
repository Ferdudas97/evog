package repository

import account.Account
import account.Login
import account.Password
import arrow.core.Either
import exceptions.DomainError
import user.User
import user.UserId


interface UserRepository {
    fun save(user: User): Either<DomainError, User>
    fun findById(id: UserId): User?
    fun update(user: User): Either<DomainError, User>
    fun delete(userId: UserId): Either<DomainError, Unit>
}

interface AccountRepository {
    fun save(account: Account): Either<DomainError, Account>
    fun findByCredentials(log: Login, password: Password): Account?
    fun update(account: Account): Either<DomainError, Account>


}