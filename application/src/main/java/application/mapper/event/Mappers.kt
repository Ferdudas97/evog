package application.mapper.event

import api.command.event.dto.*
import domain.account.model.user.info.FirstName
import domain.account.model.user.info.LastName
import domain.event.model.Event
import domain.event.model.EventId
import domain.event.model.EventName
import domain.event.model.ImageName
import domain.event.model.details.*
import domain.event.model.participiant.Age
import domain.event.model.participiant.Guest
import domain.event.model.participiant.Participant
import domain.event.model.participiant.ParticipantId
import java.util.*


fun EventDto.toDomain() = Event(id = EventId(id ?: UUID.randomUUID().toString()),
        name = EventName(name),
        imageName = ImageName(imageName),
        details = details.toDomain(),
        status = status,
        guests = guest.map { it.toDomain() }.toSet(),
        organizers =  organizers.toDomain()
)


fun EventDetailsDto.toDomain() = EventDetails(
        ageLimit = AgeLimit(minAllowedAge?.let { Age(it) }, maxAllowedAge?.let { Age(it) }),
        description = description?.let { Description(it) },
        localization = localization.toDomain(),
        period = Period(startDate, endTime),
        peopleLimit = PeopleLimit(minNumberOfPeople, maxNumberOfPeople),
        category = category)

fun Event.toDto() = EventDto(id = id.value,
        imageName = imageName.value,
        name = name.value,
        status = status,
        organizers = organizers.toDto(),
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
        imageName = imageName.value,
        localization = details.localization.toDto(),
        startTime = details.period.startTime,
        endTime = details.period.endTime,
        id = id.value,
        maxNumberOfPeople = details.peopleLimit.maxNumber,
        minNumberOfPeople = details.peopleLimit.minNumber,
        numberOfGuests = guests.size + 1,
        category = details.category)


private fun LocalizationDto.toDomain() = Localization(GeoPoint(Longitude(longitude), Latitude(latitude)))
private fun Localization.toDto() = LocalizationDto(latitude = point.latitude.value, longitude = point.longitude.value)
fun Guest.toDto() = ParticipantDto(id = id.id, firstName = firstName.value, lastName = lastName.value, age = age.int)
fun Participant.toDto() = ParticipantDto(id = id.id, firstName = firstName.value, lastName = lastName.value, age = age.int)
private fun ParticipantDto.toDomain() = Participant(ParticipantId(id), FirstName(firstName), LastName(lastName), Age(age))
