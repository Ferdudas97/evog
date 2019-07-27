package event.validation

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.left
import arrow.core.right
import event.model.*
import event.model.participiant.Organizator
import exceptions.ValidationError


fun Event.validate(): Either<ValidationError, Event> = localization.validate()
        .flatMap { organizers.validete() }
        .map { this }


const val ORGANIZERS_VALIDATION_MSG = "event should have minimum 1 organizer"

private fun Set<Organizator>.validete(): Either<ValidationError, Set<Organizator>> = if (this.size > 1) {
    this.right()
} else ValidationError(ORGANIZERS_VALIDATION_MSG).left()


fun Localization.validate() = point.validate().map { this }

fun GeoPoint.validate(): Either<ValidationError, GeoPoint> {

    return latitude.vaildate()
            .flatMap { longitude.validate() }
            .map { this }

}


const val LATITUDE_VALIDATION_MSG = "Latitude should be in -90.0 and 90.0 but is equal "
fun Latitude.vaildate(): Either<ValidationError, Latitude> = when (value) {
    in (-90.0..90.0) -> this.right()
    else -> ValidationError(LATITUDE_VALIDATION_MSG + value).left()
}

const val LONGITUDE_VALIDATION_MSG = "Longitude should be in -180.0 and 180.0 but is equal "
fun Longitude.validate() = when (value) {
    in (-180.0..180.0) -> this.right()
    else -> ValidationError(LONGITUDE_VALIDATION_MSG + value).left()
}
