package org.agh.eaiib.db.entity.user

import domain.account.model.user.info.Sex
import org.bson.codecs.pojo.annotations.BsonId
import java.time.LocalDate


data class UserEntity(
        @BsonId
        val id: String,
        val firstName: String,
        val filesId : List<String>,
        val lastName: String,
        val birthDate: LocalDate,
        val description: String? = null,
        val sex: Sex,
        val phoneNumber: String? = null,
        val email: String? = null)