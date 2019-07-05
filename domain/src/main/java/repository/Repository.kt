package repository

import account.Account
import account.Login
import user.User
import user.UserId


interface UserRepository: CrudRepository<User, UserId>

interface AccountRepository: CrudRepository<Account, Login>