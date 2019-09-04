package domain.event.repository

import arrow.core.Either
import arrow.core.Option
import arrow.core.Try
import domain.event.filter.EventFilter
import domain.event.model.Event
import domain.event.model.EventId
import exceptions.DomainError


interface EventRepository {
    suspend fun save(event: Event): Either<DomainError, Event>
    suspend fun findById(id: EventId): Option<Event>
    suspend fun delete(id: EventId): Try<Unit>
    suspend fun update(event: Event): Either<DomainError, Event>
    suspend fun filtered(filter: EventFilter): List<Event>

}