package db.mapper.user

import domain.account.model.Account
import domain.account.model.Login
import domain.account.model.Password
import org.agh.eaiib.db.entity.account.AccountEntity


fun Account.toEntity(): AccountEntity = AccountEntity(
        login = login.value,
        password = password.value,
        user = user.toEntity()

)

fun AccountEntity.toDomain(): Account = Account(login = Login(login),
        password = Password(password),
        user = user.toDomain())
