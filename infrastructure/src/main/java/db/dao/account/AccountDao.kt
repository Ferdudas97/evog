package db.dao.account

import org.agh.eaiib.db.dao.GenericDao
import org.agh.eaiib.db.entity.account.AccountEntity
import org.agh.eaiib.db.entity.user.UserEntity

interface AccountDao : GenericDao<AccountEntity, String> {
    suspend fun findByCredentials(login: String, password: String): AccountEntity?
    suspend fun updateUser(userEntity: UserEntity)
    suspend fun findUserById(userId: String): UserEntity?
}