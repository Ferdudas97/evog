package application.command.event.handler

import api.command.event.EventCommand
import api.command.event.EventCommandHandler
import api.command.event.result.EventResult
import application.command.event.msg.EventMessage
import application.command.event.toDomain
import arrow.core.Either
import arrow.core.flatMap
import arrow.peek
import domain.event.model.EventId
import domain.event.repository.EventRepository
import exceptions.DomainError
import exceptions.ItemNotFoundError
import integration.DomainEvent


class UpdateEventHandler(private val eventRepository: EventRepository,
                         private val eventSender: (DomainEvent) -> Unit) : EventCommandHandler<EventCommand.Update, EventResult.Updated>() {

    override suspend fun handle(command: EventCommand.Update): Either<DomainError, EventResult.Updated> {
        return eventRepository.findById(EventId(command.eventId))
                .map { event -> event.copy(details = command.details.toDomain()) }
                .toEither { ItemNotFoundError("Event with id = ${command.eventId} doesn't exists") }
                .flatMap { event -> eventRepository.update(event) }
                .peek { eventSender(EventMessage.Updated(it)) }
                .map { EventResult.Updated(it.id.value) }
    }
}