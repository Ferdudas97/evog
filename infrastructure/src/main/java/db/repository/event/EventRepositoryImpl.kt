package org.agh.eaiib.db.repository.event

import arrow.core.*
import domain.event.model.Event
import domain.event.model.EventId
import domain.event.repository.EventRepository
import domain.event.validation.validate
import exceptions.DomainError
import exceptions.SavingError
import exceptions.UpdateError
import org.agh.eaiib.db.dao.event.EventDao
import org.agh.eaiib.db.mapper.event.toDomain
import org.agh.eaiib.db.mapper.event.toEntity


class EventRepositoryImpl(private val eventDao: EventDao) : EventRepository {
    override suspend fun save(event: Event): Either<DomainError, Event> = event.validate()
            .map { it.toEntity() }
            .flatMap { eventEntity ->
                Try { eventDao.save(eventEntity) }.toEither {
                    SavingError(it.message ?: "Cannot save event")
                }
            }
            .map { event }

    override suspend fun findById(id: EventId): Option<Event> {
        return eventDao.findById(id.value)?.toDomain()?.toOption() ?: None
    }

    override suspend fun delete(id: EventId): Try<Unit> {
        return Try { eventDao.delete(id.value) }
    }

    override suspend fun update(event: Event): Either<DomainError, Event> {
        return event.validate()
                .map { it.toEntity() }
                .flatMap { eventEntity ->
                    Try { eventDao.save(eventEntity) }.toEither {
                        UpdateError(it.message ?: "Cannot update event")
                    }
                }
                .map { event }
    }
}