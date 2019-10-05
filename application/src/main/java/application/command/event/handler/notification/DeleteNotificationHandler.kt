package application.command.event.handler.notification

import api.command.event.EventCommand
import api.command.event.EventCommandHandler
import api.command.event.result.EventResult
import arrow.core.Either
import domain.notification.NotificationId
import domain.notification.repository.NotificationRepository
import exceptions.DomainError

class DeleteNotificationHandler(private val notificationRepository: NotificationRepository)
    : EventCommandHandler<EventCommand.Notification.Delete, EventResult.Delete>() {

    override suspend fun handle(command: EventCommand.Notification.Delete): Either<DomainError, EventResult.Delete> {
        val id = NotificationId(command.notificationId)
        return notificationRepository.delete(id)
                .map { EventResult.Delete(id.value) }
    }
}