package domain.event.model.details

import domain.event.model.participiant.Age
import java.time.LocalDateTime

data class Description(val text: String)
data class PeopleLimit(val minNumber: Int,
                       val maxNumber: Int)

enum class Category {
    SPORT, PARTY, OTHER
}

data class Period(
        val startTime: LocalDateTime,
        val endTime: LocalDateTime
)


data class EventDetails(val minimumAgeAllowed: Age?,
                        val maximumAgeAllowed: Age?,
                        val description: Description?,
                        val localization: Localization,
                        val peopleLimit: PeopleLimit?,
                        val period: Period,
                        val category: Category)