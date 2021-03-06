package application.command.event.handler

import api.command.event.EventCommand
import api.command.event.EventCommandHandler
import api.command.event.result.EventResult
import api.toEither
import application.mapper.event.toDomain
import arrow.core.Either
import arrow.core.flatMap
import domain.event.model.EventId
import domain.event.repository.EventRepository
import exceptions.DomainError
import exceptions.ItemNotFoundError


class UpdateEventHandler(private val eventRepository: EventRepository) : EventCommandHandler<EventCommand.Update, EventResult.Updated>() {

    override suspend fun handle(command: EventCommand.Update): Either<DomainError, EventResult.Updated> {
        return eventRepository.findById(EventId(command.eventId))
                ?.let { event -> event.copy(details = command.details.toDomain()) }
                .toEither { ItemNotFoundError("Event with id = ${command.eventId} doesn't exists") }
                .flatMap { event -> eventRepository.update(event) }
                .map { EventResult.Updated(it.id.value) }
    }
}