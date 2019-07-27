package account.model.user

import account.model.user.info.ContactInfo
import account.model.user.info.PersonalInfo

data class UserId(val id: String)
data class User(
        val id: UserId,
        val personalInfo: PersonalInfo,
        val contactInfo: ContactInfo)