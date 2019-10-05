package application.command.event

import arrow.core.Either
import arrow.core.left
import domain.notification.Notification
import domain.notification.NotificationId
import domain.notification.repository.NotificationRepository
import exceptions.DomainError
import exceptions.ItemNotFoundError


class NotificationService(private val notificationRepository: NotificationRepository) {

    suspend fun changeState(id: NotificationId, createRespond: (Notification) -> Notification): Either<DomainError, Notification> {
        val notification = notificationRepository.findById(id) ?: return ItemNotFoundError("").left()
        notificationRepository.delete(id)
        return createRespond(notification)
                .run { notificationRepository.save(this)}
    }
}