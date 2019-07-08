package user.info

import org.joda.time.DateTime


data class FirstName(val value: String)
data class LastName(val value: String)
data class BirthDate(val date: DateTime)
enum class Sex{
    MALE, FEMALE
}
data class Description(val value: String)

data class PersonalInfo(val firstName: FirstName,
                        val lastName: LastName,
                        val birthDate: BirthDate,
                        val description: Description?,
                        val sex: Sex)