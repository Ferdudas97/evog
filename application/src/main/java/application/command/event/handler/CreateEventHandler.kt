package application.command.event.handler

import api.command.event.EventCommand
import api.command.event.EventCommandHandler
import api.command.event.result.EventResult
import application.command.event.msg.EventMessage
import application.command.event.toDomain
import arrow.core.Either
import arrow.peek
import domain.event.repository.EventRepository
import exceptions.DomainError
import integration.DomainEvent


class CreateEventHandler(private val eventRepository: EventRepository,
                         private val sendEvent: (DomainEvent) -> Unit) : EventCommandHandler<EventCommand.Create, EventResult.Created>() {

    override suspend fun handle(command: EventCommand.Create): Either<DomainError, EventResult.Created> {
        return command.eventDto
                .toDomain()
                .let { eventRepository.save(it) }
                .peek { sendEvent(EventMessage.Created(it)) }
                .map { event -> EventResult.Created(event.id.value) }
    }

}