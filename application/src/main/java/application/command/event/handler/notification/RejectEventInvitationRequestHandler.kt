package application.command.event.handler.notification

import api.command.event.EventCommand
import api.command.event.EventCommandHandler
import api.command.event.result.EventResult
import application.command.event.NotificationService
import arrow.core.Either
import domain.notification.*
import exceptions.DomainError
import java.time.LocalDateTime
import java.util.*


class RejectEventInvitationRequestHandler(private val service: NotificationService) :
        EventCommandHandler<EventCommand.Notification.Reject, EventResult.Rejected>() {
    override suspend fun handle(command: EventCommand.Notification.Reject): Either<DomainError, EventResult.Rejected> {
        return service.changeState(NotificationId(command.notificationId), this::createRejectedNotification)
                .map { EventResult.Rejected(it.id.value) }
    }

    private fun createRejectedNotification(notification: Notification): Notification {
        return Notification(
                id = NotificationId(UUID.randomUUID().toString()),
                receiver = notification.sender,
                sender = notification.receiver,
                eventId = notification.eventId,
                content = Content("${notification.receiver.firstName}: Hej, niestety nie możesz dołączyc do wydarzenia"),
                state = State.REJECTED,
                creationTime = CreationTime(LocalDateTime.now())
        )
    }
}