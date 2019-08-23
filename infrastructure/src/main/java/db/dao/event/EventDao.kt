package org.agh.eaiib.db.dao.event

import org.agh.eaiib.db.dao.GenericDao
import org.agh.eaiib.db.entity.event.EventEntity
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.updateOne


interface EventDao : GenericDao<EventEntity, String>


class EventDaoImpl(val db: CoroutineDatabase) : EventDao {
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
