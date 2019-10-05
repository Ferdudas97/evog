package application.mapper

import api.command.event.dto.ParticipantDto
import api.command.notification.dto.NotificationDto
import domain.event.model.participiant.Participant
import domain.notification.Notification


fun Notification.toDto() = NotificationDto(
        id = id.value,
        guest = sender.toDto(),
        eventId = eventId.value,
        content = content.value,
        state = state,
        creationTime = creationTime.localDateTime
)

private fun Participant.toDto() = ParticipantDto(id.id, firstName.value, lastName.value, age.int)