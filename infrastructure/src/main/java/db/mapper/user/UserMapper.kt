package db.mapper.user

import domain.account.model.user.FileId
import domain.account.model.user.User
import domain.account.model.user.UserId
import domain.account.model.user.info.*
import org.agh.eaiib.db.entity.user.UserEntity


fun User.toEntity(): UserEntity = UserEntity(
        id = id.id,
        firstName = personalInfo.firstName.value,
        lastName = personalInfo.lastName.value,
        description = personalInfo.description?.value,
        birthDate = personalInfo.birthDate.date,
        sex = personalInfo.sex,
        filesId = files.map { it.id },
        email = contactInfo.email?.value,
        phoneNumber = contactInfo.phoneNumber?.number
)

fun UserEntity.toDomain(): User = User(id = UserId(id),
        personalInfo = PersonalInfo(FirstName(firstName),
                LastName(lastName),
                BirthDate(birthDate),
                description?.let { Description(it) },
                sex),
        files = filesId.map { FileId(it) },
        contactInfo = ContactInfo(phoneNumber = phoneNumber?.let { PhoneNumber(it) },
                email = email?.let { Email(it) }))



