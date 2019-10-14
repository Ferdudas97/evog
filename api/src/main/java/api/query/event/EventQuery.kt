package api.query.event

import api.command.event.dto.EventFilterDto
import query.Query
import query.QueryHandler


sealed class EventQuery : Query {
    data class FindById(val eventId: String, val userId: String) : EventQuery()
    data class FindBy(val filter: EventFilterDto) : EventQuery()
}

interface EventQueryHandler<Q : EventQuery, R> : QueryHandler<Q, R>