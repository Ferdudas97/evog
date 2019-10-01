package application.mapper.event

import api.command.event.dto.EventFilterDto
import api.command.event.dto.LocalizationDto
import domain.event.filter.EventFilter
import domain.event.filter.Range
import domain.event.model.details.Latitude
import domain.event.model.details.Longitude
import domain.event.model.participiant.Age


fun EventFilterDto.toDomain() = EventFilter(name = name,
        ageRange = Range(min = Age(minAllowedAge ?: Int.MIN_VALUE),
                max = Age(maxAllowedAge ?: Int.MAX_VALUE)),
        timeRange = Range(min = startTime, max = endTime),
        peopleRange = Range(min = minNumberOfPeople ?: Int.MIN_VALUE, max = maxNumberOfPeople ?: Int.MAX_VALUE),
        latitudeRange = localization.computeLatitudeRange(radius = localizationRadius),
        longitudeRange = localization.computeLongitudeRange(radius = localizationRadius))


private fun geographicDelta(km: Double) = km / 111
// todo: zrobić to zgodnie z
private fun LocalizationDto.computeLongitudeRange(radius: Double): Range<Longitude> {
    return Range(min = Longitude(this.longitude - geographicDelta(radius))
            , max = Longitude(this.longitude + geographicDelta(radius)))
}

private fun LocalizationDto.computeLatitudeRange(radius: Double): Range<Latitude> {
    return Range(min = Latitude(this.latitude - geographicDelta(radius))
            , max = Latitude(this.latitude + geographicDelta(radius)))

}