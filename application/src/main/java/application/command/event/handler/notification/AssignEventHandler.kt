package application.command.event.handler.notification

import api.command.event.EventCommand
import api.command.event.EventCommandHandler
import api.command.event.result.EventResult
import application.mapper.user.toParticipant
import arrow.core.Either
import arrow.core.Option
import arrow.core.Some
import arrow.core.left
import domain.account.model.user.User
import domain.account.model.user.UserId
import domain.account.repository.UserRepository
import domain.event.model.Event
import domain.event.model.EventId
import domain.event.model.participiant.Participant
import domain.event.repository.EventRepository
import domain.notification.*
import domain.notification.repository.NotificationRepository
import exceptions.DomainError
import exceptions.UserIsAlreadyAssigned
import java.time.LocalDateTime
import java.util.*

class AssignEventHandler(private val userRepository: UserRepository,
                         private val eventRepository: EventRepository,
                         private val notificationRepository: NotificationRepository)
    : EventCommandHandler<EventCommand.Notification.Assign, EventResult.Assign>() {

    override suspend fun handle(command: EventCommand.Notification.Assign): Either<DomainError, EventResult.Assign> {
        val event = EventId(command.eventId).let { eventRepository.findById(it) }!!
        val user = UserId(command.userId).let { userRepository.findById(it) }!!
        if (event.isAssigned(user.id)) {
            val saveResult = createNotification(event, user, event.organizers)
                    .let { notificationRepository.save(it) }
            return when (saveResult) {
                is Either.Left -> saveResult
                is Either.Right -> saveResult.map { EventResult.Assign(it.id.value) }
            }
        } else {
            return UserIsAlreadyAssigned("${user.id} is aready assinged").left()
        }
    }

    private fun createNotification(event: Event, user: User, organizator: Participant): Notification {
        return Notification(
                id = NotificationId(UUID.randomUUID().toString()),
                receiver = organizator,
                sender = user.toParticipant(),
                creationTime = CreationTime(LocalDateTime.now()),
                content = Content("Hej, chciałbym dołączyć do twojego wydarzenia ${event.name.value}"),
                state = State.NOT_ACTION,
                eventId = event.id)
    }
}

private fun <T> Option<T>.ifPresent(f: (T) -> Unit) = when (this) {
    is Some -> f(this.t)
    else -> Unit
}