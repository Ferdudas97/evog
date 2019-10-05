package org.agh.eaiib.db.mapper.event

import domain.account.model.user.info.FirstName
import domain.account.model.user.info.LastName
import domain.event.model.Event
import domain.event.model.EventId
import domain.event.model.EventName
import domain.event.model.details.*
import domain.event.model.participiant.*
import org.agh.eaiib.db.entity.event.DetailsEntity
import org.agh.eaiib.db.entity.event.EventEntity
import org.agh.eaiib.db.entity.event.ParticipiantEntity

fun EventEntity.toDomain() = Event(id = EventId(id),
        name = EventName(name),
        details = details.toDomain(),
        guests = guests.map { it.toGuest() }.toSet(),
        status = status,
        organizers = organizers.toOrganizer())


fun ParticipiantEntity.toGuest() = Guest(ParticipantId(id), FirstName(firstName), LastName(lastName), Age(age))
fun ParticipiantEntity.toOrganizer() = Organizator(ParticipantId(id), FirstName(firstName), LastName(lastName), Age(age))

private fun DetailsEntity.toDomain() = EventDetails(ageLimit = AgeLimit(minAllowedAge?.let { Age(it) }, maxAllowedAge?.let { Age(it) }),
        description = description?.let(::Description),
        localization = Localization(GeoPoint(Longitude(longitude),
                Latitude(latitude))),
        peopleLimit = PeopleLimit(minNumberOfPeople, maxNumberOfPeople),
        category = category,
        period = Period(startDate, endTime))

fun Event.toEntity() = EventEntity(id = id.value,
        name = name.value,
        guests = guests.map { it.toEntity() }.toSet(),
        organizers = organizers.toEntity(),
        status = status,
        details = details.toEntity())

private fun EventDetails.toEntity() = DetailsEntity(minAllowedAge = ageLimit.min?.int,
        maxAllowedAge = ageLimit.max?.int,
        maxNumberOfPeople = peopleLimit.maxNumber,
        minNumberOfPeople = peopleLimit.minNumber,
        description = description?.text,
        startDate = period.startTime,
        endTime = period.endTime,
        category = category, longitude = localization.point.longitude.value,
        latitude = localization.point.latitude.value)

fun Participant.toEntity() = ParticipiantEntity(id.id, firstName.value, lastName.value, age.int)
