package domain.account.repository

import arrow.core.Either
import domain.account.model.Account
import domain.account.model.Login
import domain.account.model.Password
import domain.account.model.user.User
import domain.account.model.user.UserId
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