package db.dao.account

import org.agh.eaiib.db.dao.GenericDao
import org.agh.eaiib.db.entity.account.AccountEntity
import org.agh.eaiib.db.entity.user.UserEntity

interface UserDao : GenericDao<UserEntity, String> {
    suspend fun findByCredentials(login: String, password: String): UserEntity?
    suspend fun updateAccount(accountEntity: AccountEntity)
}