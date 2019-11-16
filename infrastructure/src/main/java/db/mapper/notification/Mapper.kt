package org.agh.eaiib.db.mapper.notification

import domain.event.model.EventId
import domain.notification.Content
import domain.notification.CreationTime
import domain.notification.Notification
import domain.notification.NotificationId
import org.agh.eaiib.db.entity.notification.NotificationEntity
import org.agh.eaiib.db.mapper.event.toDomain
import org.agh.eaiib.db.mapper.event.toEntity


fun Notification.toEntity() = NotificationEntity(
        id = id.value,
        receiver = receiver.toEntity(),
        sender = sender.toEntity(),
        eventId = eventId.value,
        content = content.value,
        state = state,
        creationTime = creationTime.localDateTime
)

fun NotificationEntity.toDomain() = Notification(
        id  = NotificationId(id),
        receiver = receiver.toDomain(),
        sender = sender.toDomain(),
        eventId = EventId(eventId),
        content = Content(content),
        state = state,
        creationTime = CreationTime(creationTime)
)