package api.command.event

import api.command.event.dto.EventDetailsDto
import api.command.event.dto.EventDto
import command.Command


sealed class EventCommand : Command() {
    data class Create(val eventDto: EventDto) : EventCommand()
    data class Cancel(val eventId: String) : EventCommand()
    data class Update(val eventId: String,
                      val details: EventDetailsDto) : EventCommand()

    sealed class Notification : EventCommand() {
        data class Assign(val eventId: String, var userId: String) : Notification()
        data class Reject(val notificationId: String) : Notification()
        data class Accept(val notificationId: String) : Notification()
        data class Delete(val notificationId: String) : Notification()
    }
}