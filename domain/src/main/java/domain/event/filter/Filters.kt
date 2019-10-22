package domain.event.filter

import domain.account.model.user.UserId
import domain.event.model.Status
import domain.event.model.details.Category
import domain.event.model.details.Latitude
import domain.event.model.details.Longitude
import domain.event.model.participiant.Age
import java.time.LocalDateTime

data class Range<T>(val min: T, val max: T) {
    fun <B> map(f:(T)->B) = Range(f(min),f(max))
}


data class EventFilter(val name: String? = null,
                       val ageRange: Range<Age>? = null,
                       val timeRange: Range<LocalDateTime>? = null,
                       val latitudeRange: Range<Latitude>? = null,
                       val longitudeRange: Range<Longitude>? = null,
                       val peopleRange: Range<Int>? = null,
                       val shouldFilterById: Boolean? = null,
                       val userId: UserId? = null,
                       val status: Status? = null,
                       val category: Category? = null)