package org.agh.eaiib.db.entity.account

import org.agh.eaiib.db.entity.user.UserEntity
import org.bson.codecs.pojo.annotations.BsonId


data class AccountEntity(
        @BsonId
        val login: String,
        val password: String,
        val user: UserEntity)