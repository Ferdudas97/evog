package application.command.event.handler.discussion

import api.command.event.EventCommand
import api.command.event.EventCommandHandler
import api.command.event.result.EventResult
import api.generateId
import application.mapper.event.toDomain
import arrow.core.Either
import arrow.core.right
import domain.event.model.Event
import domain.event.model.EventId
import domain.event.model.discussion.Message
import domain.event.model.participiant.Participant
import domain.event.repository.EventRepository
import domain.notification.Notification
import domain.notification.NotificationId
import domain.notification.repository.NotificationRepository
import exceptions.DomainError

class AddMessageHandler(private val eventRepository: EventRepository,
                        private val notificationRepository: NotificationRepository)
    : EventCommandHandler<EventCommand.Discussion.AddMessage, EventResult.MessageAdded>() {
    override suspend fun handle(command: EventCommand.Discussion.AddMessage): Either<DomainError, EventResult.MessageAdded> {
        val eventId = EventId(command.eventId)
        val event = eventRepository.findById(eventId)!!
        val message = command.messageDto.toDomain()
        val updatedEvent = event.addMessage(message = message)
        val updateResult = eventRepository.update(updatedEvent)
        return when (updateResult) {
            is Either.Left -> updateResult
            is Either.Right -> saveNotifications(updatedEvent, message).right()
                    .map { EventResult.MessageAdded }
        }

    }

    private suspend fun saveNotifications(event: Event, message: Message) {
        event.participants().filter { it.id != message.creator.id }
                .map { createNotification(event = event, receiver = it, message = message) }
                .forEach {
                    notificationRepository.save(it)
                }
    }

    private fun createNotification(event: Event, receiver: Participant, message: Message) = Notification(
            id = NotificationId(generateId()),
            eventId = event.id,
            sender = message.creator,
            content = message.content,
            creationTime = message.createdAt,
            receiver = receiver
    )
}