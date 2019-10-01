package domain.event.filter

import domain.event.model.details.Category
import domain.event.model.details.Latitude
import domain.event.model.details.Longitude
import domain.event.model.participiant.Age
import java.time.LocalDateTime

data class Range<T>(val min: T, val max: T) {
    fun <B> map(f:(T)->B) = Range(f(min),f(max))
}


data class EventFilter(val name: String? = null,
                       val ageRange: Range<Age>,
                       val timeRange: Range<LocalDateTime>,
                       val latitudeRange: Range<Latitude>,
                       val longitudeRange: Range<Longitude>,
                       val peopleRange: Range<Int>,
                       val category: Category? = null)