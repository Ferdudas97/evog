package org.agh.eaiib.db.entity.user

import domain.account.model.user.info.Sex
import org.joda.time.DateTime


data class UserEntity(val id: String,
                      val firstName: String,
                      val lastName: String,
                      val birthDate: DateTime,
                      val description: String? = null,
                      val sex: Sex,
                      val phoneNumber: String? = null,
                      val email: String? = null)