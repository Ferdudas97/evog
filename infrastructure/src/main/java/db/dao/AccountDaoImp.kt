package org.agh.eaiib.db.dao

import org.agh.eaiib.db.Accounts
import org.agh.eaiib.db.entity.account.AccountEntity
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import org.joda.time.DateTime
import java.util.*


class AccountDaoImpl(private val userDao: UserDao) : AccountDao {
    override fun update(entity: AccountEntity) {
        transaction {
            Accounts.update {
                it[Accounts.login] = entity.login
                it[Accounts.password] = entity.password
                it[Accounts.userId] = UUID.fromString(entity.user.id)
                userDao.update(entity.user)

            }

        }
    }

    override fun save(entity: AccountEntity) {
        transaction {
            Accounts.insert {
                it[Accounts.login] = entity.login
                it[Accounts.password] = entity.password
                it[Accounts.userId] = UUID.fromString(entity.user.id)
                it[Accounts.creationDate] = DateTime.now()
                userDao.save(entity.user)
            }

        }
    }

    override fun findByCredentials(login: String, password: String): AccountEntity? {
        var entity: AccountEntity? = null
        transaction {
            entity = Accounts.select { (Accounts.login eq login) and (Accounts.password eq password) }
                    .map(Accounts::toAccountEntity)
                    .firstOrNull()
            commit()
        }
        return entity
    }

}