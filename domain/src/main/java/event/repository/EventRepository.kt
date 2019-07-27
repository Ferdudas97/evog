package event.repository

import arrow.core.Either
import arrow.core.Option
import event.model.Event
import event.model.EventId
import exceptions.DomainError


interface EventRepository {
    fun save(event: Event): Either<DomainError, Event>
    fun findById(id: EventId): Option<Event>
    fun delete(event: Event): Either<DomainError, Event>
    fun update(event: Event): Either<DomainError, Event>

}