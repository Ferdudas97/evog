package domain.event.filter

import domain.account.model.user.UserId
import domain.event.model.Status
import domain.event.model.details.Category
import domain.event.model.details.GeoPoint
import domain.event.model.participiant.Age
import java.time.LocalDateTime

data class Range<T>(val min: T, val max: T) {
    fun <B> map(f:(T)->B) = Range(f(min),f(max))
}


data class EventFilter(val name: String? = null,
                       val ageRange: Range<Age>? = null,
                       val geoPoint: GeoPoint,
                       val radius: Int,
                       val timeRange: Range<LocalDateTime>? = null,
                       val peopleRange: Range<Int>? = null,
                       val shouldFilterById: Boolean? = null,
                       val userId: UserId? = null,
                       val status: Status? = null,
                       val category: Category? = null) {
    fun isInRadius(longitude: Double, latitude: Double): Boolean {
        // algorithm from https://www.movable-type.co.uk/scripts/latlong.html
        val apiLatInRadians = Math.toRadians(geoPoint.latitude.value)
        val apiLongInRadians = Math.toRadians(geoPoint.longitude.value)
        val latInRadians = Math.toRadians(latitude)
        val longInRadians = Math.toRadians(longitude)
        val latDelta = apiLatInRadians - latInRadians
        val longDelta = apiLongInRadians - longInRadians
        val a = Math.sin(latDelta / 2) * Math.sin(latDelta / 2) + Math.cos(apiLatInRadians) * Math.cos(latInRadians) *
                Math.sin(longDelta / 2) * Math.sin(longDelta / 2)
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))

        val distance =  earthRadius * c
        return radius< distance

    }
}

const val earthRadius: Long = 6371