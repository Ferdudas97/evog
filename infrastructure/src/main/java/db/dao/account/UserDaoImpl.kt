package db.dao.account

import org.agh.eaiib.db.entity.account.AccountEntity
import org.agh.eaiib.db.entity.user.UserEntity
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.div
import org.litote.kmongo.eq

class UserDaoImpl(val db: CoroutineDatabase) : UserDao {
    override suspend fun updateAccount(accountEntity: AccountEntity) {
        val fromDb = findByCredentials(accountEntity.login, accountEntity.password)
        fromDb?.let {
            db.getUser().updateOneById(it, it.copy(account = accountEntity))
        }
    }

    override suspend fun findByCredentials(login: String, password: String): UserEntity? {
        return db.getUser()
                .findOne(UserEntity::account / AccountEntity::login eq login,
                        UserEntity::account / AccountEntity::password eq password)

    }

    override suspend fun findById(id: String): UserEntity? {
        return db.getUser()
                .findOneById(id)
    }

    override suspend fun save(entity: UserEntity) {
        db.getUser()
                .save(entity)
    }

    override suspend fun delete(id: String) {
        db.getUser()
                .deleteOneById(id)
    }

    override suspend fun update(entity: UserEntity) {
        db.getUser()
                .updateOneById(entity.id, entity)
    }

    private fun CoroutineDatabase.getUser() = getCollection<UserEntity>()


}