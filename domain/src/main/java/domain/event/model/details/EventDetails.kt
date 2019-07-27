package domain.event.model.details

import domain.event.model.participiant.Age

data class Description(val text: String)
data class PeopleLimit(val minNumber: Int,
                       val maxNumber: Int)

enum class Category {
    SPORT, PARTY, OTHER
}


data class EventDetails(val minimumAgeAllowed: Age,
                        val maximumAgeAllowed: Age,
                        val description: Description,
                        val peopleLimit: PeopleLimit,
                        val category: Category)