package org.agh.eaiib.db.entity.user

import org.joda.time.DateTime
import user.info.Sex


data class UserEntity(val id: String,
                      val firstName: String,
                      val lastName: String,
                      val birthDate: DateTime,
                      val description: String?,
                      val sex: Sex,
                      val phoneNumber: String?,
                      val email: String?)