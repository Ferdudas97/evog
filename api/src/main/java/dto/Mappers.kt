package dto

import account.Account
import account.Login
import account.Password
import user.User
import user.UserId
import user.info.*
import java.util.*


object AccountDtoMapper {
    fun mapToDto(account: Account): AccountDto = AccountDto(
            login = account.login.value,
            password = account.password.value,
            user = UserDtoMapper.mapToEntity(account.user)

    )

    fun mapToDomain(dto: AccountDto): Account = Account(login = Login(dto.login),
            password = Password(dto.password),
            user = UserDtoMapper.mapToDomain(dto.user))
}

object UserDtoMapper {

    fun mapToEntity(user: User): UserDto = UserDto(
            id = user.id.id,
            firstName = user.personalInfo.firstName.value,
            lastName = user.personalInfo.lastName.value,
            description = user.personalInfo.description?.value,
            birthDate = user.personalInfo.birthDate.date,
            sex = user.personalInfo.sex,
            email = user.contactInfo.email?.value,
            phoneNumber = user.contactInfo.phoneNumber?.number
    )

    fun mapToDomain(dto: UserDto): User = User(id = UserId(dto.id ?: UUID.randomUUID().toString()),
            personalInfo = PersonalInfo(FirstName(dto.firstName),
                    LastName(dto.lastName), BirthDate(dto.birthDate),
                    Description(dto.description!!),
                    dto.sex),
            contactInfo = ContactInfo(phoneNumber = dto.phoneNumber?.let { PhoneNumber(it) },
                    email = dto.email?.let { Email(it) }))
}