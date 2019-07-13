package org.agh.eaiib.db.dao

import org.agh.eaiib.db.entity.account.AccountEntity

interface AccountDao {
    fun save(entity: AccountEntity)
    fun findByCredentials(login: String, password: String): AccountEntity?
    fun update(entity: AccountEntity)

}