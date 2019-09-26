package api.command.event.result

import api.command.event.dto.EventDto
import command.CommandResult

sealed class EventResult : CommandResult() {
    data class Created(val event: EventDto) : EventResult()
    data class Updated(val id: String) : EventResult()
    data class Canceled(val id: String) : EventResult()

}
