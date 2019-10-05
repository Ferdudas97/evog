package org.agh.eaiib.db.repository.notification

import arrow.core.Either
import arrow.core.Try
import domain.account.model.user.UserId
import domain.notification.Notification
import domain.notification.NotificationId
import domain.notification.repository.NotificationRepository
import exceptions.DeleteError
import exceptions.SavingError
import exceptions.UpdateError
import org.agh.eaiib.db.dao.notification.NotificationDao
import org.agh.eaiib.db.mapper.notification.toDomain
import org.agh.eaiib.db.mapper.notification.toEntity


class NotificationRepositoryImpl(private val notificationDao: NotificationDao) : NotificationRepository {
    override suspend fun save(notification: Notification): Either<SavingError, Notification> {
        return Try { notificationDao.save(notification.toEntity()) }
                .toEither()
                .mapLeft { SavingError("Cannot save notification $notification") }
                .map { notification }

    }

    override suspend fun findById(notificationId: NotificationId): Notification? {
        return notificationDao.findById(notificationId.value)?.toDomain()
    }

    override suspend fun findAllByUserId(userId: UserId): Set<Notification> {
        return notificationDao.findAllUserNotification(userId.id)
                .map { it.toDomain() }
                .toSet()
    }

    override suspend fun update(notification: Notification): Either<UpdateError, Unit> {
        return Try { notificationDao.update(notification.toEntity()) }
                .toEither()
                .mapLeft { UpdateError(it.message ?: "Cannot uptade $notification") }
    }

    override suspend fun delete(notificationId: NotificationId): Either<DeleteError, Unit> {
        return Try { notificationDao.delete(notificationId.value) }
                .toEither()
                .mapLeft { DeleteError(it.message ?: "Cannot delete notificiation with id = $notificationId") }
    }
}