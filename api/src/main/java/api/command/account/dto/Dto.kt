package api.command.account.dto

import com.fasterxml.jackson.annotation.JsonFormat
import domain.account.model.user.info.Sex
import java.time.LocalDate


data class AccountDto(val credentials: CredentialsDto,
                      val user: UserDto)

data class UserDto(val id: String?,
                   val photo: ByteArray? = null,
                   val firstName: String,
                   val lastName: String,
                   @JsonFormat(pattern = "yyyy-MM-dd")
                   val birthDate: LocalDate,
                   val description: String? = null,
                   val sex: Sex,
                   val phoneNumber: String? = null,
                   val email: String? = null) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserDto

        if (id != other.id) return false
        if (photo != null) {
            if (other.photo == null) return false
            if (!photo.contentEquals(other.photo)) return false
        } else if (other.photo != null) return false
        if (firstName != other.firstName) return false
        if (lastName != other.lastName) return false
        if (birthDate != other.birthDate) return false
        if (description != other.description) return false
        if (sex != other.sex) return false
        if (phoneNumber != other.phoneNumber) return false
        if (email != other.email) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (photo?.contentHashCode() ?: 0)
        result = 31 * result + firstName.hashCode()
        result = 31 * result + lastName.hashCode()
        result = 31 * result + birthDate.hashCode()
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + sex.hashCode()
        result = 31 * result + (phoneNumber?.hashCode() ?: 0)
        result = 31 * result + (email?.hashCode() ?: 0)
        return result
    }
}

data class CredentialsDto(val login: String,
                          val password: String)
