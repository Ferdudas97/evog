package org.agh.eaiib.db.entity.account
import org.agh.eaiib.db.entity.user.UserEntity
import org.joda.time.DateTime


data class AccountEntity(
        val login: String,
        val password: String,
        val creationDate: DateTime,
        val user: UserEntity
)