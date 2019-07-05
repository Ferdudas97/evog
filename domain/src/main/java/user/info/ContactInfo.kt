package user.info

data class PhoneNumber(val number: String)

data class Email(val value: String)

data class Website(val value: String)

data class ContactInfo(val phoneNumber: PhoneNumber?,
                       val email: Email?,
                       val websites: List<Website> = listOf())