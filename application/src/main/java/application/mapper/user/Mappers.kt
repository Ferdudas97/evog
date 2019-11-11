package application.mapper.user

import api.command.account.dto.AccountDto
import api.command.account.dto.CredentialsDto
import api.command.account.dto.UserDto
import domain.account.model.Account
import domain.account.model.Login
import domain.account.model.Password
import domain.account.model.user.FileId
import domain.account.model.user.User
import domain.account.model.user.UserId
import domain.account.model.user.info.*
import domain.event.model.participiant.Age
import domain.event.model.participiant.Participant
import domain.event.model.participiant.ParticipantId
import java.util.*


fun Account.toDto(): AccountDto = AccountDto(
        credentials = CredentialsDto(login.value, password.value),
        user = user.toDto()
)

fun AccountDto.toDomain(): Account = Account(login = Login(credentials.login),
        password = Password(credentials.password), user = user.toDomain())


fun User.toDto(): UserDto = UserDto(
        id = id.id,
        photosId = files.map { it.id },
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
                description?.let(::Description), sex),
        files = photosId(),
        contactInfo = ContactInfo(phoneNumber = phoneNumber?.let { PhoneNumber(it) },
                email = email?.let { Email(it) }))

const val DEFAULT_MALE_PHOTO = "defaultMale"
const val DEFAULT_FEMALE_PHOTO = "defaultFemale"
private fun UserDto.photosId() = if (photosId.isEmpty()) listOf(getDefaultPhotoId()) else photosId.map { FileId(it) }

private fun UserDto.getDefaultPhotoId() = when(sex) {
    Sex.MALE -> FileId(DEFAULT_MALE_PHOTO)
    Sex.FEMALE -> FileId(DEFAULT_FEMALE_PHOTO)

}

fun User.toParticipant() = Participant(ParticipantId(id.id),
        firstName = this.personalInfo.firstName,
        lastName = this.personalInfo.lastName,
        age = Age(this.getAge()),
        fileId = files.first())
