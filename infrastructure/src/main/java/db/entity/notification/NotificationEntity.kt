package org.agh.eaiib.db.entity.notification

import domain.notification.State
import org.agh.eaiib.db.entity.event.ParticipiantEntity
import org.bson.codecs.pojo.annotations.BsonId
import java.time.LocalDateTime


data class NotificationEntity(
        @BsonId
        val id: String,
        val receiver: ParticipiantEntity,
        val sender: ParticipiantEntity,
        val eventId: String,
        val content: String,
        val state: State,
        val creationTime: LocalDateTime
)