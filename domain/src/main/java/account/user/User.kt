package account.user

import account.user.info.ContactInfo
import account.user.info.PersonalInfo

data class UserId(val id: String)
data class User(
        val id: UserId,
        val personalInfo: PersonalInfo,
        val contactInfo: ContactInfo)