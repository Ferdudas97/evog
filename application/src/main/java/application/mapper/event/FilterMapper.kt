package application.mapper.event

import api.command.event.dto.EventFilterDto
import api.command.event.dto.LocalizationDto
import domain.account.model.user.UserId
import domain.event.filter.EventFilter
import domain.event.filter.Range
import domain.event.model.details.GeoPoint
import domain.event.model.details.Latitude
import domain.event.model.details.Longitude
import domain.event.model.participiant.Age


fun EventFilterDto.toDomain(userId: UserId) = EventFilter(name = name,
        ageRange = Range(min = Age(minAllowedAge ?: Int.MIN_VALUE),
                max = Age(maxAllowedAge ?: Int.MAX_VALUE)),
        timeRange = Range(min = startTime, max = endTime),
        category = category,
        shouldFilterById = isAssigned,
        userId = userId,
        peopleRange = Range(min = minNumberOfPeople ?: Int.MIN_VALUE, max = maxNumberOfPeople ?: Int.MAX_VALUE),
        geoPoint = localization.toGeoPoint() ,
        radius = this.localizationRadius.toInt()
)


private fun LocalizationDto.toGeoPoint() = GeoPoint(Longitude(longitude),Latitude(latitude))
