package application.command.event.handler

import api.command.event.EventCommand
import api.command.event.EventCommandHandler
import api.command.event.result.EventResult
import application.mapper.event.toDomain
import application.mapper.event.toDto
import arrow.core.Either
import domain.event.repository.EventRepository
import exceptions.DomainError



class CreateEventHandler(private val eventRepository: EventRepository) : EventCommandHandler<EventCommand.Create, EventResult.Created>() {

    override suspend fun handle(command: EventCommand.Create): Either<DomainError, EventResult.Created> {
        return command.eventDto
                .toDomain()
                .let { eventRepository.save(it) }
                .map { event -> EventResult.Created(event.toDto()) }
    }

}