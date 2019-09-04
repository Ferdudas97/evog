package application.mapper.user

import api.command.account.dto.AccountDto
import api.command.account.dto.UserDto
import domain.account.model.Account
import domain.account.model.Login
import domain.account.model.Password
import domain.account.model.user.User
import domain.account.model.user.UserId
import domain.account.model.user.info.*
import java.util.*


fun Account.toDto(): AccountDto = AccountDto(
        login = login.value,
        password = password.value,
        user = user.toDto()
)

fun AccountDto.toDomain(): Account = Account(login = Login(login),
        password = Password(password), user = user.toDomain())


fun User.toDto(): UserDto = UserDto(
        id = id.id,
        firstName = personalInfo.firstName.value,
        lastName = personalInfo.lastName.value,
        description = personalInfo.description?.value,
        birthDate = personalInfo.birthDate.date,
        sex = personalInfo.sex,
        email = contactInfo.email?.value,
        phoneNumber = contactInfo.phoneNumber?.number
)

fun UserDto.toDomain(): User = User(id = UserId(id ?: UUID.randomUUID().toString()),
        personalInfo = PersonalInfo(FirstName(firstName),
                LastName(lastName), BirthDate(birthDate),
                Description(description!!), sex),
        contactInfo = ContactInfo(phoneNumber = phoneNumber?.let { PhoneNumber(it) },
                email = email?.let { Email(it) }))
