package application.command.event.handler

import api.command.event.EventCommand
import api.command.event.EventCommandHandler
import api.command.event.result.EventResult
import api.toEither
import arrow.core.Either
import arrow.core.flatMap
import domain.event.model.EventId
import domain.event.model.Status
import domain.event.repository.EventRepository
import exceptions.DomainError
import exceptions.ItemNotFoundError


class CancelEventHandler(private val eventRepository: EventRepository) : EventCommandHandler<EventCommand.Cancel, EventResult.Canceled>() {
    override suspend fun handle(command: EventCommand.Cancel): Either<DomainError, EventResult.Canceled> {
        return eventRepository.findById(id = EventId(command.eventId))
                .toEither { ItemNotFoundError("Event with id = ${command.eventId} doesn't exists") }
                .map { event -> event.copy(status = Status.CANCELED) }
                .flatMap { event -> eventRepository.save(event) }
                .map { EventResult.Canceled(command.eventId) }

    }
}