package domain.notification.repository

import arrow.core.Either
import domain.account.model.user.UserId
import domain.notification.Notification
import domain.notification.NotificationId
import exceptions.DeleteError
import exceptions.SavingError
import exceptions.UpdateError

interface NotificationRepository {
    suspend fun save(notificationId: Notification): Either<SavingError, Notification>
    suspend fun findById(notificationId: NotificationId): Notification?
    suspend fun findAllByUserId(userId: UserId): Set<Notification>
    suspend fun update(notification: Notification): Either<UpdateError, Unit>
    suspend fun delete(notificationId: NotificationId): Either<DeleteError, Unit>

}