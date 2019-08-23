package application.command.account

import api.command.account.dto.AccountDto
import api.command.account.dto.UserDto
import domain.account.model.Account
import domain.account.model.Login
import domain.account.model.Password
import domain.account.model.user.User
import domain.account.model.user.UserId
import domain.account.model.user.info.*
import java.util.*


object AccountDtoMapper {
    fun mapToDto(account: Account): AccountDto = AccountDto(
            login = account.login.value,
            password = account.password.value
    )

    fun mapToDomain(dto: AccountDto): Account = Account(login = Login(dto.login),
            password = Password(dto.password))
}

object UserDtoMapper {

    fun mapToDto(user: User): UserDto = UserDto(
            id = user.id.id,
            firstName = user.personalInfo.firstName.value,
            lastName = user.personalInfo.lastName.value,
            description = user.personalInfo.description?.value,
            birthDate = user.personalInfo.birthDate.date,
            sex = user.personalInfo.sex,
            email = user.contactInfo.email?.value,
            phoneNumber = user.contactInfo.phoneNumber?.number,
            accountDto = AccountDtoMapper.mapToDto(user.account)

    )

    fun mapToDomain(dto: UserDto): User = User(id = UserId(dto.id ?: UUID.randomUUID().toString()),
            personalInfo = PersonalInfo(FirstName(dto.firstName),
                    LastName(dto.lastName), BirthDate(dto.birthDate),
                    Description(dto.description!!),
                    dto.sex),
            contactInfo = ContactInfo(phoneNumber = dto.phoneNumber?.let { PhoneNumber(it) },
                    email = dto.email?.let { Email(it) }),
            account = AccountDtoMapper.mapToDomain(dto.accountDto))
}