package api.command.account.dto

import com.fasterxml.jackson.annotation.JsonFormat
import domain.account.model.user.info.Sex
import java.time.LocalDate


data class AccountDto(val credentials: CredentialsDto,
                      val user: UserDto)

data class UserDto(val id: String?,
                   val photosId : List<String> = emptyList(),
                   val firstName: String,
                   val lastName: String,
                   @JsonFormat(pattern = "yyyy-MM-dd")
                   val birthDate: LocalDate,
                   val description: String? = null,
                   val sex: Sex,
                   val phoneNumber: String? = null,
                   val email: String? = null)

data class CredentialsDto(val login: String,
                          val password: String)
