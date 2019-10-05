package application.command.event.handler.notification

import api.command.event.EventCommandHandler
import api.command.event.result.EventResult
import application.command.event.NotificationService
import arrow.core.Either
import arrow.core.flatMap
import domain.event.model.Event
import domain.event.repository.EventRepository
import domain.notification.*
import exceptions.DomainError
import java.time.LocalDateTime
import java.util.*
import api.command.event.EventCommand.Notification as NotificationCommand


class AcceptEventInvitationRequestHandler(private val notificationService: NotificationService,
                                          private val eventRepository: EventRepository) : EventCommandHandler<NotificationCommand.Accept, EventResult.Accepted>() {
    override suspend fun handle(command: NotificationCommand.Accept): Either<DomainError, EventResult.Accepted> {
        val id = NotificationId(command.notificationId)
        return notificationService.changeState(id, this::createAcceptNotification )
                .flatMap { addUserAsGuest(it) }
                .map { EventResult.Accepted(id.value) }
    }

    private fun createAcceptNotification(notification: Notification) : Notification {
        return  Notification(
                id = NotificationId(UUID.randomUUID().toString()),
                receiver = notification.sender,
                sender = notification.receiver,
                eventId = notification.eventId,
                content = Content("${notification.receiver.firstName}: Hej, dołączyłes do wydarzenia"),
                state = State.ACCEPTED,
                creationTime = CreationTime(LocalDateTime.now())
        )
    }
    private suspend fun addUserAsGuest(notification: Notification): Either<DomainError, Event> {
        val event = notification.eventId.let { eventRepository.findById(it) }!!
        val updatedEvent = event.run {
            copy(guests = guests + notification.sender)
        }
        return eventRepository.update(updatedEvent)
    }
}