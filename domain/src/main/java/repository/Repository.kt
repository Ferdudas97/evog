package repository

import account.Account
import account.Login
import account.Password
import user.User
import user.UserId


interface UserRepository: CrudRepository<User, UserId>

interface AccountRepository {
    fun save(account: Account)
    fun updatePassword(log: Login, password: Password)
    fun findByCredentials(log: Login, password: Password): Account?

}