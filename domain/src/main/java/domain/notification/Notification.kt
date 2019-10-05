package domain.notification

import domain.event.model.EventId
import domain.event.model.participiant.Participant
import java.time.LocalDateTime

data class Content(val value: String)
data class CreationTime(val localDateTime: LocalDateTime)
data class NotificationId(val value: String)
enum class State {
    REJECTED,ACCEPTED, NOT_ACTION
}


data class Notification(
        val id: NotificationId,
        val receiver: Participant,
        val sender: Participant,
        val eventId: EventId,
        val content: Content,
        val state: State,
        val creationTime: CreationTime)