package org.agh.eaiib.db.mapper.event

import api.generateId
import domain.account.model.user.FileId
import domain.account.model.user.info.FirstName
import domain.account.model.user.info.LastName
import domain.event.model.Event
import domain.event.model.EventId
import domain.event.model.EventName
import domain.event.model.ImageName
import domain.event.model.details.*
import domain.event.model.discussion.Discussion
import domain.event.model.discussion.Message
import domain.event.model.participiant.Age
import domain.event.model.participiant.Participant
import domain.event.model.participiant.ParticipantId
import domain.notification.Content
import domain.notification.CreationTime
import org.agh.eaiib.db.entity.event.*

fun EventEntity.toDomain() = Event(id = EventId(id),
        name = EventName(name),
        imageName = ImageName(imageName),
        details = details.toDomain(),
        guests = guests.map { it.toDomain() }.toSet(),
        status = status,
        organizers = organizers.toDomain(),
        discussion = discussionEntity.toDomain())

private fun DiscussionEntity.toDomain() = Discussion(messages = messages.map { it.toDomain() })

private fun MessageEntity.toDomain() = Message(createdAt = CreationTime(createdAt),
        content = Content(text), creator = participant.toDomain())


fun ParticipiantEntity.toDomain() = Participant(ParticipantId(id),
        firstName = FirstName(firstName),
        lastName = LastName(lastName), fileId = FileId(fileId), age = Age(age))


private fun DetailsEntity.toDomain() = EventDetails(ageLimit = AgeLimit(minAllowedAge?.let { Age(it) }, maxAllowedAge?.let { Age(it) }),
        description = description?.let(::Description),
        localization = Localization(GeoPoint(Longitude(longitude),
                Latitude(latitude))),
        peopleLimit = PeopleLimit(minNumberOfPeople, maxNumberOfPeople),
        category = category,
        period = Period(startDate, endTime))

fun Event.toEntity() = EventEntity(id = id.value,
        name = name.value,
        imageName = imageName.value,
        guests = guests.map { it.toEntity() }.toSet(),
        organizers = organizers.toEntity(),
        status = status,
        details = details.toEntity(),
        discussionEntity = discussion.toEntity())

private fun Discussion.toEntity() = DiscussionEntity(messages = messages.map { it.toEntity() },
        id = generateId())
private fun Message.toEntity() = MessageEntity(text = content.value,
        participant = creator.toEntity(),
        createdAt = createdAt.localDateTime,
        id = generateId())

private fun EventDetails.toEntity() = DetailsEntity(minAllowedAge = ageLimit.min?.int,
        maxAllowedAge = ageLimit.max?.int,
        maxNumberOfPeople = peopleLimit.maxNumber,
        minNumberOfPeople = peopleLimit.minNumber,
        description = description?.text,
        startDate = period.startTime,
        endTime = period.endTime,
        category = category, longitude = localization.point.longitude.value,
        latitude = localization.point.latitude.value)

fun Participant.toEntity() = ParticipiantEntity(id.id, firstName.value, lastName.value, fileId.id, age.int)
