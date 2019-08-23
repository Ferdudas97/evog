package api.command.event.result

import command.CommandResult

sealed class EventResult : CommandResult() {
    data class Created(val id: String) : EventResult()
    data class Updated(val id: String) : EventResult()
    data class Canceled(val id: String) : EventResult()

}
