package org.agh.eaiib.db.dao.event

import domain.event.filter.EventFilter
import domain.event.filter.Range
import org.agh.eaiib.db.dao.GenericDao
import org.agh.eaiib.db.entity.event.EventEntity
import org.agh.eaiib.db.entity.event.ParticipiantEntity
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.updateOne
import java.time.LocalDateTime


interface EventDao : GenericDao<EventEntity, String> {
    suspend fun filtered(eventFilter: EventFilter): List<EventEntity>
}


class EventDaoImpl(val db: CoroutineDatabase) : EventDao {
    override suspend fun filtered(eventFilter: EventFilter): List<EventEntity> {
        return db.getEvents().find().toList().filter { eventEntity -> filter(eventFilter, eventEntity) }
    }


    //    override suspend fun filtered(eventFilter: EventFilter): List<EventEntity> {
//        return eventFilter.run {
//            db.getEvents().aggregate<EventEntity>(
//                    match(
////                            EventEntity::name eq name,
//                            EventEntity::details / DetailsEntity::endTime lte timeRange.max,
//                            EventEntity::details / DetailsEntity::startDate gte timeRange.min,
//                            EventEntity::details / DetailsEntity::maxAllowedAge lte ageRange.max?.int,
//                            EventEntity::details / DetailsEntity::minAllowedAge gte ageRange.min?.int,
//                            EventEntity::details / DetailsEntity::maxNumberOfPeople lte peopleRange.max,
//                            EventEntity::details / DetailsEntity::minNumberOfPeople gte peopleRange.min,
//                            EventEntity::details / DetailsEntity::category eq category,
//                            EventEntity::details / DetailsEntity::latitude lte latitudeRange.max?.value,
//                            EventEntity::details / DetailsEntity::latitude gte latitudeRange.min?.value,
//                            EventEntity::details / DetailsEntity::longitude lte longitudeRange.max?.value,
//                            EventEntity::details / DetailsEntity::longitude gte longitudeRange.min?.value
//                    )
//            )
//                    .toList()
//
//        }
//    }

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

    private fun filter(eventFilter: EventFilter, entity: EventEntity): Boolean {
        eventFilter.apply {
            val isContainsName = name?.let { entity.name.contains(it) or it.contains(entity.name) } ?: true
            entity.details.let {
                val isPeople = this.peopleRange?.isBeetwen(it.minNumberOfPeople.orMin(), it.maxNumberOfPeople.orMax())
                        ?: true
                val isAge = this.ageRange?.map { v -> v.int }?.isBeetwen(it.minAllowedAge.orMin(), it.maxAllowedAge.orMax())
                        ?: true
                val isLatitude = this.latitudeRange?.map { v -> v.value }?.isBeetwen(it.latitude, it.latitude) ?: true
                val isLongitude = this.longitudeRange?.map { v -> v.value }?.isBeetwen(it.longitude, it.longitude)
                        ?: true
                val isTime = this.timeRange?.isBeetwen(it.startDate) ?: true
                val isCategory = this.category?.equals(it.category) ?: true
                val isStatus = status?.equals(entity.status) ?: true
                val isAssigned = if (shouldFilterById ?: false) {
                    val participants = entity.guests + entity.organizers
                    participants.map(ParticipiantEntity::id).contains(userId!!.id)
                } else true
                return isTime and isContainsName and isPeople and isAge and isLatitude and isLongitude and isCategory and isAssigned and isStatus
            }

        }
    }

    private fun Int?.orMax() = this ?: Int.MAX_VALUE

    private fun Int?.orMin() = this ?: Int.MIN_VALUE
    private fun Range<Double>.isBeetwen(oMin: Double, oMax: Double): Boolean {
        return (max >= oMax) and (min <= oMin)
    }

    private fun Range<LocalDateTime>.isBeetwen(time: LocalDateTime): Boolean {
        return (max.isAfter(time)) and (min.isBefore(time))
    }


    private fun Range<Int>.isBeetwen(oMin: Int, oMax: Int): Boolean {
        return (max >= oMax) and (min <= oMin)
    }

    private fun CoroutineDatabase.getEvents() = getCollection<EventEntity>("events")
}
