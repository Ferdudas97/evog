package api.command.event

import api.command.Command
import api.command.event.dto.EventDetailsDto
import api.command.event.dto.EventDto
import api.command.event.dto.MessageDto


sealed class EventCommand : Command() {
    data class Create(val eventDto: EventDto) : EventCommand()
    data class Cancel(val eventId: String) : EventCommand()
    data class Update(val eventId: String,
                      val details: EventDetailsDto) : EventCommand()

    data class RemoveGuest(val eventId: String, val guestId: String, val userId: String) : EventCommand()

    sealed class Discussion() {
        data class AddMessage(val eventId: String,
                              val messageDto: MessageDto) : EventCommand()
    }
    sealed class Notification : EventCommand() {
        data class Assign(val eventId: String, var userId: String) : Notification()
        data class Reject(val notificationId: String) : Notification()
        data class Accept(val notificationId: String) : Notification()
        data class Delete(val notificationId: String) : Notification()
    }
}