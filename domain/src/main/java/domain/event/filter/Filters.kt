package domain.event.filter

import domain.account.model.user.UserId
import domain.event.model.Status
import domain.event.model.details.Category
import domain.event.model.details.GeoPoint
import domain.event.model.participiant.Age
import java.time.LocalDateTime
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

data class Range<T>(val min: T, val max: T) {
    fun <B> map(f:(T)->B) = Range(f(min),f(max))
}


data class EventFilter(val name: String? = null,
                       val ageRange: Range<Age>? = null,
                       val geoPoint: GeoPoint? = null,
                       val radius: Int = 0,
                       val timeRange: Range<LocalDateTime>? = null,
                       val peopleRange: Range<Int>? = null,
                       val shouldFilterById: Boolean? = null,
                       val userId: UserId? = null,
                       val status: Status? = null,
                       val category: Category? = null) {
    fun isInRadius(longitude: Double, latitude: Double): Boolean {
        if (geoPoint == null ) return  true
        // algorithm from https://www.movable-type.co.uk/scripts/latlong.html
        val apiLatInRadians = Math.toRadians(geoPoint.latitude.value)
        val apiLongInRadians = Math.toRadians(geoPoint.longitude.value)
        val latInRadians = Math.toRadians(latitude)
        val longInRadians = Math.toRadians(longitude)
        val latDelta = apiLatInRadians - latInRadians
        val longDelta = apiLongInRadians - longInRadians
        val a = sin(latDelta / 2) * sin(latDelta / 2) + cos(apiLatInRadians) * cos(latInRadians) *
                sin(longDelta / 2) * sin(longDelta / 2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        val distance =  earthRadius * c
        return radius < distance

    }
}

const val earthRadius: Long = 6371 * 1000