package user

import user.info.ContactInfo
import user.info.PersonalInfo

data class UserId(val id: String)
data class User(
        val id: UserId,
        val personalInfo: PersonalInfo,
        val contactInfo: ContactInfo)