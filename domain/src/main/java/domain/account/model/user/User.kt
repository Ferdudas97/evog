package domain.account.model.user

import domain.account.model.user.info.ContactInfo
import domain.account.model.user.info.PersonalInfo

data class UserId(val id: String)
data class User(
        val id: UserId,
        val personalInfo: PersonalInfo,
        val contactInfo: ContactInfo)