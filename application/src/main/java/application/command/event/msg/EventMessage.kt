package application.command.event.msg

import domain.event.model.Event
import integration.DomainEvent


sealed class EventMessage : DomainEvent {
    data class Created(val event: Event) : EventMessage()
    data class Updated(val event: Event) : EventMessage()
}