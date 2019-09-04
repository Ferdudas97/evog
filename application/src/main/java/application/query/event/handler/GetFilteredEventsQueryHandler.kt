package application.query.event.handler

import api.command.event.dto.EventList
import api.query.event.EventQuery
import api.query.event.EventQueryHandler
import application.mapper.event.toDomain
import application.mapper.event.toSnapshotList
import domain.event.repository.EventRepository


class GetFilteredEventsQueryHandler(private val eventRepository: EventRepository) : EventQueryHandler<EventQuery.FindBy, EventList> {
    override suspend fun exevute(query: EventQuery.FindBy): EventList {
        val filter = query.filter.toDomain()
        return eventRepository.filtered(filter).toSnapshotList()
    }
}