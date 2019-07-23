package org.agh.eaiib.db.entity.user

import org.joda.time.DateTime
import user.info.Sex


data class UserEntity(val id: String,
                      val firstName: String,
                      val lastName: String,
                      val birthDate: DateTime,
                      val description: String? = null,
                      val sex: Sex,
                      val phoneNumber: String? = null,
                      val email: String? = null)