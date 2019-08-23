package org.agh.eaiib.db.mapper

import domain.account.model.user.User
import domain.account.model.user.UserId
import domain.account.model.user.info.*
import org.agh.eaiib.db.entity.user.UserEntity


object UserMapper {

    fun mapToEntity(user: User): UserEntity = UserEntity(
            id = user.id.id,
            firstName = user.personalInfo.firstName.value,
            lastName = user.personalInfo.lastName.value,
            description = user.personalInfo.description?.value,
            birthDate = user.personalInfo.birthDate.date,
            sex = user.personalInfo.sex,
            email = user.contactInfo.email?.value,
            phoneNumber = user.contactInfo.phoneNumber?.number,
            account = AccountMapper.mapToEntity(user.account)
    )

    fun mapToDomain(entity: UserEntity): User = User(id = UserId(entity.id),
            personalInfo = PersonalInfo(FirstName(entity.firstName),
                    LastName(entity.lastName),
                    BirthDate(entity.birthDate),
                    entity.description?.let { Description(it) },
                    entity.sex),
            account = AccountMapper.mapToDomain(entity.account),
            contactInfo = ContactInfo(phoneNumber = entity.phoneNumber?.let { PhoneNumber(it) },
                    email = entity.email?.let { Email(it) }))
}



