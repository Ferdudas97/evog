package api.command.event

import api.command.event.dto.EventDetailsDto
import api.command.event.dto.EventDto
import command.Command


sealed class EventCommand : Command() {
    data class Create(val eventDto: EventDto) : EventCommand()
    data class Cancel(val eventId: String) : EventCommand()
    data class Update(val eventId: String,
                      val details: EventDetailsDto) : EventCommand()
}