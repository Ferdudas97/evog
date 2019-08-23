package application.command.event.handler

import api.command.event.EventCommand
import api.command.event.EventCommandHandler
import api.command.event.result.EventResult
import application.command.event.msg.EventMessage
import arrow.core.Either
import arrow.core.Left
import domain.event.model.EventId
import domain.event.model.Status
import domain.event.repository.EventRepository
import exceptions.DomainError
import exceptions.ItemNotFoundError
import exceptions.ValidationError


class CancelEventHandler(val eventRepository: EventRepository, sendEvent: (EventMessage) -> Unit) : EventCommandHandler<EventCommand.Cancel, EventResult.Canceled>() {
    override suspend fun handle(command: EventCommand.Cancel): Either<DomainError, EventResult.Canceled> {
        eventRepository.findById(id = EventId(command.eventId))
                .toEither { ItemNotFoundError("Event with id = ${command.eventId} doesn't exists") }
                .map { event -> event.copy(status = Status.CANCELED) }
                .map { event -> eventRepository.save(event) }
                .map { }

        return Left(ValidationError("ad"))
    }
}