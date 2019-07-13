package dto

import org.joda.time.DateTime
import user.info.Sex


data class AccountDto(val login: String, val password: String, val user: UserDto)

data class UserDto(val id: String?,
                   val firstName: String,
                   val lastName: String,
                   val birthDate: DateTime,
                   val description: String?,
                   val sex: Sex,
                   val phoneNumber: String?,
                   val email: String?)
