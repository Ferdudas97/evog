package org.agh.eaiib.db.mapper

import account.Account
import account.CreationDate
import account.Login
import account.Password
import org.agh.eaiib.db.entity.account.AccountEntity


object AccountMapper {
    fun mapToEntity(account: Account): AccountEntity = AccountEntity(
            login = account.login.value,
            password = account.password.value,
            creationDate = account.creationDate.date,
            user = UserMapper.mapToEntity(account.user)

            )

    fun mapToDomain(entity: AccountEntity): Account = Account(login = Login(entity.login),
            password = Password(entity.password),
            creationDate = CreationDate(entity.creationDate),
            user = UserMapper.mapToDomain(entity.user))
}