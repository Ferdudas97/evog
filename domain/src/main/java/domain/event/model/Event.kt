package domain.event.model

import domain.account.model.user.UserId
import domain.event.model.details.EventDetails
import domain.event.model.discussion.Discussion
import domain.event.model.discussion.Message
import domain.event.model.participiant.Participant


data class EventId(val value: String)

enum class Status {
    CANCELED, BEFORE, NOTIFIED, AFTER, TAKE_PLACE
}

data class ImageName(val value: String)
data class EventName(val value: String)
data class Event(val id: EventId,
                 val name: EventName,
                 val imageName: ImageName,
                 val details: EventDetails,
                 val guests: Set<Participant> = setOf(),
                 val status: Status,
                 val discussion: Discussion,
                 val organizers: Participant) {
    fun isAssigned(userId: UserId) = (guests + organizers).map { it.id.id }.contains(userId.id)
    fun addMessage(message: Message) = copy(discussion = discussion.copy(
            messages = discussion.messages + message)
    )
    fun participants() = guests + organizers


}


