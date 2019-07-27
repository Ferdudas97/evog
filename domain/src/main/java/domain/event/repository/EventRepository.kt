package domain.event.repository

import arrow.core.Either
import arrow.core.Option
import domain.event.model.Event
import domain.event.model.EventId
import exceptions.DomainError


interface EventRepository {
    fun save(event: Event): Either<DomainError, Event>
    fun findById(id: EventId): Option<Event>
    fun delete(event: Event): Either<DomainError, Event>
    fun update(event: Event): Either<DomainError, Event>

}