package application.query.event.handler

import api.command.event.dto.EventDto
import api.query.event.EventQuery
import api.query.event.EventQueryHandler
import application.mapper.event.toDto
import domain.event.model.EventId
import domain.event.repository.EventRepository


class FindEventByIdQueryHandler(private val eventRepository: EventRepository) : EventQueryHandler<EventQuery.FindById, EventDto?> {
    override suspend fun exevute(query: EventQuery.FindById): EventDto? {
        return eventRepository.findById(EventId(query.id))?.toDto()
    }
}