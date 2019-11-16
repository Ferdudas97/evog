package api.command.event.result

import api.command.CommandResult
import api.command.event.dto.EventDto

sealed class EventResult : CommandResult() {
    data class Created(val event: EventDto) : EventResult()
    data class Updated(val id: String) : EventResult()
    data class Canceled(val id: String) : EventResult()
    data class Assign(val notificationId: String) : EventResult()
    data class Accepted(val notificationId: String) : EventResult()
    data class Rejected(val notificationId: String) : EventResult()
    data class Delete(val notificationId: String) : EventResult()
    object GuestRemoved : EventResult()
    object MessageAdded : EventResult()

}
