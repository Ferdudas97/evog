package application.mapper.event

import api.command.event.dto.*
import domain.account.model.user.info.FirstName
import domain.account.model.user.info.LastName
import domain.event.model.Event
import domain.event.model.EventId
import domain.event.model.EventName
import domain.event.model.details.*
import domain.event.model.participiant.Age
import domain.event.model.participiant.Guest
import domain.event.model.participiant.Organizator
import domain.event.model.participiant.ParticipantId
import java.util.*


fun EventDto.toDomain() = Event(id = EventId(id ?: UUID.randomUUID().toString()),
        name = EventName(name),
        details = details.toDomain(),
        status = status,
        guests = guest.map { it.toGuest() }.toSet(),
        organizers = organizers.map { it.toOrganizer() }.toSet()
)


fun EventDetailsDto.toDomain() = EventDetails(
        ageLimit = AgeLimit(minAllowedAge?.let { Age(it) }, maxAllowedAge?.let { Age(it) }),
        description = description?.let { Description(it) },
        localization = localization.toDoamin(),
        period = Period(startDate, endTime),
        peopleLimit = PeopleLimit(minNumberOfPeople, maxNumberOfPeople),
        category = category)

fun Event.toDto() = EventDto(id = id.value,
        name = name.value,
        status = status,
        organizers = organizers.map { it.toDto() }.toSet(),
        guest = guests.map { it.toDto() }.toSet(),
        details = details.toDto())

private fun EventDetails.toDto() = EventDetailsDto(minAllowedAge = ageLimit.min?.int,
        maxAllowedAge = ageLimit.max?.int,
        minNumberOfPeople = peopleLimit.minNumber,
        maxNumberOfPeople = peopleLimit.maxNumber,
        endTime = period.endTime,
        startDate = period.startTime,
        description = description?.text,
        localization = localization.toDto(),
        category = category)


fun List<Event>.toSnapshotList() = this.map { it.toSnapshotDto() }

private fun Event.toSnapshotDto() = EventSnapshot(name = name.value,
        localization = details.localization.toDto(),
        maxNumberOfPeople = details.peopleLimit.maxNumber,
        minNumberOfPeople = details.peopleLimit.minNumber,
        numberOfGuests = guests.size + organizers.size,
        category = details.category)


private fun LocalizationDto.toDoamin() = Localization(GeoPoint(Longitude(longitude), Latitude(latitude)))
private fun Localization.toDto() = LocalizationDto(latitude = point.latitude.value, longitude = point.longitude.value)
private fun Guest.toDto() = ParticipantDto(id = id.id, firstName = firstName.value, lastName = lastName.value, age = age.int)
private fun Organizator.toDto() = ParticipantDto(id = id.id, firstName = firstName.value, lastName = lastName.value, age = age.int)
private fun ParticipantDto.toGuest() = Guest(ParticipantId(id), FirstName(firstName), LastName(lastName), Age(age))
private fun ParticipantDto.toOrganizer() = Organizator(ParticipantId(id), FirstName(firstName), LastName(lastName), Age(age))