package org.agh.eaiib.db.repository

import account.Account
import account.Login
import account.Password
import com.sun.org.apache.bcel.internal.generic.Select
import org.agh.eaiib.db.Accounts
import org.agh.eaiib.db.Users
import org.agh.eaiib.db.entity.account.AccountEntity
import org.agh.eaiib.db.entity.user.UserEntity
import org.agh.eaiib.db.mapper.AccountMapper
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import repository.AccountRepository
import repository.UserRepository
import user.UserId
import java.util.*


class AccountRepositoryImpl : AccountRepository {
    override fun findByCredentials(log: Login, password: Password): Account? {
        var entity: AccountEntity? = null
        transaction {
            entity = Accounts.select { (Accounts.login eq log.value) and (Accounts.password eq password.value) }
                    .map(Accounts::toAccountEntity)
                    .firstOrNull()

            commit()
        }
        return entity?.let(AccountMapper::mapToDomain)
    }


    override fun updatePassword(log: Login, password: Password) {
        transaction {
            Accounts.update(where = { Accounts.login eq log.value },
                    limit = 1,
                    body = { it[Accounts.password] = password.value })
            commit()
        }
    }

    override fun save(account: Account) {
        val entity = AccountMapper.mapToEntity(account)
        transaction {
            Accounts.insert {
                it[Accounts.login] = entity.login
                it[Accounts.password] = entity.password
                it[Accounts.userId] = UUID.fromString(entity.user.id)
                it[Accounts.creationDate] = entity.creationDate
            }
            commit()
        }
    }


}