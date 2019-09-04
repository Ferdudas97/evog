package org.agh.eaiib.db.dao.event

import domain.event.filter.EventFilter
import org.agh.eaiib.db.dao.GenericDao
import org.agh.eaiib.db.entity.event.DetailsEntity
import org.agh.eaiib.db.entity.event.EventEntity
import org.litote.kmongo.*
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.aggregate
import org.litote.kmongo.coroutine.updateOne


interface EventDao : GenericDao<EventEntity, String> {
    suspend fun filtered(eventFilter: EventFilter): List<EventEntity>
}


class EventDaoImpl(val db: CoroutineDatabase) : EventDao {

    override suspend fun filtered(eventFilter: EventFilter): List<EventEntity> {
        return eventFilter.run {
            db.getEvents().aggregate<EventEntity>(
                    match(
                            EventEntity::name eq name,
                            EventEntity::details / DetailsEntity::endTime eq timeRange.max,
                            EventEntity::details / DetailsEntity::startDate eq timeRange.min,
                            EventEntity::details / DetailsEntity::maxAllowedAge lte ageRange.max?.int,
                            EventEntity::details / DetailsEntity::minAllowedAge gte ageRange.min?.int,
                            EventEntity::details / DetailsEntity::maxNumberOfPeople lte peopleRange.max,
                            EventEntity::details / DetailsEntity::minNumberOfPeople gte peopleRange.min,
                            EventEntity::details / DetailsEntity::category eq category,
                            EventEntity::details / DetailsEntity::latitude lte latitudeRange.max?.value,
                            EventEntity::details / DetailsEntity::latitude gte latitudeRange.min?.value,
                            EventEntity::details / DetailsEntity::longitude lte longitudeRange.max?.value,
                            EventEntity::details / DetailsEntity::longitude gte longitudeRange.min?.value
                    )
            )
                    .toList()

        }
    }

    override suspend fun findById(id: String): EventEntity? {
        return db.getEvents()
                .findOneById(id)
    }

    override suspend fun save(entity: EventEntity) {
        db.getEvents()
                .save(entity)
    }

    override suspend fun delete(id: String) {
        db.getEvents().deleteOneById(id)
    }

    override suspend fun update(entity: EventEntity) {
        db.getEvents().updateOne(entity)
    }


    private fun CoroutineDatabase.getEvents() = getCollection<EventEntity>("events")
}
