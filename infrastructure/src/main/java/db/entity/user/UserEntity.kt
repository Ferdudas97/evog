package org.agh.eaiib.db.entity.user

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.joda.time.DateTime
import user.info.*
import java.time.LocalDate
import java.util.*





data class UserEntity(val id: String,
                      val firstName: String,
                      val lastName: String,
                      val birthDate: DateTime,
                      val description: String?,
                      val sex: Sex,
                      val phoneNumber: String?,
                      val email: String?)