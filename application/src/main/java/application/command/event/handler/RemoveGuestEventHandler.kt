package application.command.event.handler

import api.command.event.EventCommand
import api.command.event.EventCommandHandler
import api.command.event.result.EventResult
import application.mapper.user.toParticipant
import arrow.core.Either
import arrow.core.left
import domain.account.model.user.UserId
import domain.account.repository.UserRepository
import domain.event.model.EventId
import domain.event.model.participiant.ParticipantId
import domain.event.repository.EventRepository
import domain.notification.Content
import domain.notification.CreationTime
import domain.notification.Notification
import domain.notification.NotificationId
import domain.notification.repository.NotificationRepository
import exceptions.CannotRemoveOrganizer
import exceptions.DomainError
import exceptions.UserNotAuthorized
import java.util.*

class RemoveGuestEventHandler(private val eventRepository: EventRepository,
                              private val userRepository: UserRepository,
                              private val notificationRepository: NotificationRepository) : EventCommandHandler<EventCommand.RemoveGuest, EventResult.GuestRemoved>() {
    override suspend fun handle(command: EventCommand.RemoveGuest): Either<DomainError, EventResult.GuestRemoved> {
        val eventId = EventId(command.eventId)
        val userId = ParticipantId(command.userId)
        val guestId = ParticipantId(command.guestId)
        val event = eventRepository.findById(eventId)!!
        val guest = userRepository.findById(UserId(guestId.id))!!.toParticipant()
        val organizator = userRepository.findById(UserId(guestId.id))!!.toParticipant()
        if (event.organizers.id != userId) return UserNotAuthorized("User ${userId.id} cant remove guests").left()
        return if (event.organizers.id != guestId) {
            val deleted = event.copy(guests = event.guests.filter { it.id != guestId }.toSet())
            eventRepository.update(deleted)
            val notification = Notification(id = NotificationId(UUID.randomUUID().toString()),
                    receiver = guest,
                    sender = organizator,
                    eventId = eventId,
                    content = Content("Niestety nie możesz wziąźć udziału w ${event.name.value}"), creationTime = CreationTime())
            notificationRepository.save(notification)
                    .map { EventResult.GuestRemoved }
        } else CannotRemoveOrganizer("Cannot remmove organizer ${organizator.id} of event ${event.id}").left()
    }
}