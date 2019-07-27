package account.repository

import account.Account
import account.Login
import account.Password
import account.user.User
import account.user.UserId
import arrow.core.Either
import exceptions.DomainError


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