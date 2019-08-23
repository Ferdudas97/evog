package api.command.account.dto

import domain.account.model.user.info.Sex
import java.time.LocalDate


data class AccountDto(val login: String, val password: String)

data class UserDto(val id: String?,
                   val firstName: String,
                   val lastName: String,
                   val birthDate: LocalDate,
                   val description: String?,
                   val sex: Sex,
                   val phoneNumber: String?,
                   val accountDto: AccountDto,
                   val email: String?)
