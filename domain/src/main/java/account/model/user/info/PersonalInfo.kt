package account.model.user.info

import org.joda.time.LocalDate


data class FirstName(val value: String)
data class LastName(val value: String)
data class BirthDate(val date: LocalDate)
enum class Sex{
    MALE, FEMALE
}
data class Description(val value: String)

data class PersonalInfo(val firstName: FirstName,
                        val lastName: LastName,
                        val birthDate: BirthDate,
                        val description: Description?,
                        val sex: Sex)