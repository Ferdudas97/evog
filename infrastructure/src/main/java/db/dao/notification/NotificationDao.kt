package org.agh.eaiib.db.dao.notification

import org.agh.eaiib.db.dao.GenericDao
import org.agh.eaiib.db.entity.event.ParticipiantEntity
import org.agh.eaiib.db.entity.notification.NotificationEntity
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.aggregate
import org.litote.kmongo.div
import org.litote.kmongo.eq
import org.litote.kmongo.match


interface NotificationDao : GenericDao<NotificationEntity, String> {
    suspend fun findAllUserNotification(userId: String): List<NotificationEntity>
}

class NotificationDaoImpl(private val db: CoroutineDatabase) : NotificationDao {
    override suspend fun findById(id: String): NotificationEntity? {
        return db.getNotification().findOneById(id)
    }

    override suspend fun save(entity: NotificationEntity) {
        db.getNotification().save(entity)
    }

    override suspend fun delete(id: String) {
        db.getNotification().deleteOneById(id)
    }

    override suspend fun update(entity: NotificationEntity) {
        db.getNotification().updateOneById(entity.id, entity)
    }

    override suspend fun findAllUserNotification(userId: String): List<NotificationEntity> {
        return db.getNotification().aggregate<NotificationEntity>(
                match(NotificationEntity::receiver / ParticipiantEntity::id eq userId)
        ).toList()
    }

    private fun CoroutineDatabase.getNotification() = db.getCollection<NotificationEntity>("notifications")
}