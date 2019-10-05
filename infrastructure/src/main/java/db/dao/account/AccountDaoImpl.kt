package db.dao.account

import org.agh.eaiib.db.entity.account.AccountEntity
import org.agh.eaiib.db.entity.user.UserEntity
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.div
import org.litote.kmongo.eq

class AccountDaoImpl(val db: CoroutineDatabase) : AccountDao {
    override suspend fun updateUser(userEntity: UserEntity) {
        val account = db.getAccount().findOne(AccountEntity::user / UserEntity::id eq userEntity.id)
        account?.copy(user = userEntity)
                ?.let { db.getAccount().updateOneById(account.login, it) }
    }

    override suspend fun findByCredentials(login: String, password: String): AccountEntity? {
        return db.getAccount()
                .findOne(AccountEntity::login eq login,
                        AccountEntity::password eq password)

    }

    override suspend fun findById(id: String): AccountEntity? {
        return db.getAccount()
                .findOneById(id)
    }

    override suspend fun save(entity: AccountEntity) {
        db.getAccount().save(entity)
    }

    override suspend fun delete(id: String) {
        db.getAccount()
                .deleteOneById(id)
    }

    override suspend fun update(entity: AccountEntity) {
        db.getAccount()
                .updateOneById(entity.login, entity)
    }

    override suspend fun findUserById(userId: String): UserEntity? {
        return db.getAccount().findOne(AccountEntity::user / UserEntity::id eq userId)?.user
    }

    private fun CoroutineDatabase.getAccount() = getCollection<AccountEntity>("accounts")


}