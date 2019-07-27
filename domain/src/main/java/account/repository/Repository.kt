package account.repository

import account.model.Account
import account.model.Login
import account.model.Password
import account.model.user.User
import account.model.user.UserId
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