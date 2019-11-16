package domain.event.model.discussion

import domain.event.model.participiant.Participant
import domain.notification.Content
import domain.notification.CreationTime

data class Message(
        val creator: Participant,
        val createdAt: CreationTime,
        val content: Content
)
data class Discussion(val messages: List<Message> = emptyList())
