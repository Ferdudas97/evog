package api.command.event.result

import api.command.event.dto.EventDto
import command.CommandResult

sealed class EventResult : CommandResult() {
    data class Created(val event: EventDto) : EventResult()
    data class Updated(val id: String) : EventResult()
    data class Canceled(val id: String) : EventResult()
    data class Assign(val notificationId: String) : EventResult()
    data class Accepted(val notificationId: String) : EventResult()
    data class Rejected(val notificationId: String) : EventResult()
    data class Delete(val notificationId: String) : EventResult()

}
