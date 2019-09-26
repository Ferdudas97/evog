package application.query.event.handler

import api.command.event.dto.EventSnapshot
import api.query.event.EventQuery
import api.query.event.EventQueryHandler
import application.mapper.event.toDomain
import application.mapper.event.toSnapshotList
import domain.event.repository.EventRepository


class GetFilteredEventsQueryHandler(private val eventRepository: EventRepository) : EventQueryHandler<EventQuery.FindBy, List<EventSnapshot>> {
    override suspend fun exevute(query: EventQuery.FindBy): List<EventSnapshot> {
        val filter = query.filter.toDomain()
        return eventRepository.filtered(filter).toSnapshotList()
    }
}