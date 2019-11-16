package api.query.event

import api.command.event.dto.EventFilterDto
import api.query.Query
import api.query.QueryHandler


sealed class EventQuery : Query {
    data class FindById(val eventId: String, val userId: String) : EventQuery()
    data class FindBy(val filter: EventFilterDto, val userId: String) : EventQuery()
}

interface EventQueryHandler<Q : EventQuery, R> : QueryHandler<Q, R>