package org.agh.eaiib.db.mapper

import domain.account.model.Account
import domain.account.model.Login
import domain.account.model.Password
import org.agh.eaiib.db.entity.account.AccountEntity


object AccountMapper {
    fun mapToEntity(account: Account): AccountEntity = AccountEntity(
            login = account.login.value,
            password = account.password.value
    )

    fun mapToDomain(entity: AccountEntity): Account = Account(login = Login(entity.login),
            password = Password(entity.password))
}