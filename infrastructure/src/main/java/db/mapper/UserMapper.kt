package org.agh.eaiib.db.mapper

import domain.account.model.user.User
import domain.account.model.user.UserId
import domain.account.model.user.info.*
import org.agh.eaiib.db.entity.user.UserEntity
import org.joda.time.LocalTime


object UserMapper {

    fun mapToEntity(user: User): UserEntity = UserEntity(
            id = user.id.id,
            firstName = user.personalInfo.firstName.value,
            lastName = user.personalInfo.lastName.value,
            description = user.personalInfo.description?.value,
            birthDate = user.personalInfo.birthDate.date.toDateTime(LocalTime.MIDNIGHT),
            sex = user.personalInfo.sex,
            email = user.contactInfo.email?.value,
            phoneNumber = user.contactInfo.phoneNumber?.number
    )

    fun mapToDomain(entity: UserEntity): User = User(id = UserId(entity.id),
            personalInfo = PersonalInfo(FirstName(entity.firstName),
                    LastName(entity.lastName),
                    BirthDate(entity.birthDate.toLocalDate()),
                    entity.description?.let { Description(it) },
                    entity.sex),
            contactInfo = ContactInfo(phoneNumber = entity.phoneNumber?.let { PhoneNumber(it) },
                    email = entity.email?.let { Email(it) }))
}