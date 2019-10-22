package application.services

import domain.event.filter.EventFilter
import domain.event.filter.Range
import domain.event.model.Event
import domain.event.model.Status
import domain.event.model.participiant.Participant
import domain.event.repository.EventRepository
import domain.notification.*
import domain.notification.repository.NotificationRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext
import java.time.LocalDateTime
import java.util.*


const val checkTime = 60 * 1000L

class IntervalActionService(private val eventRepository: EventRepository, private val notificationRepository: NotificationRepository) {

    private val context = newFixedThreadPoolContext(1, "backgorundPool")


    fun executeActions() {
        GlobalScope.launch {
            while (true) {
                markAsPast()
                sendReminderNotification()
                delay(checkTime)
            }
        }
    }

    private suspend fun markAsPast() {
            val filter = EventFilter(timeRange = Range(max = LocalDateTime.now(), min = LocalDateTime.MIN))
            eventRepository.filtered(filter)
                    .map { it.copy(status = Status.AFTER) }
                    .forEach {
                        eventRepository.update(it)
                    }
    }

    private suspend fun sendReminderNotification() {
        val filter = EventFilter(timeRange = Range(max = LocalDateTime.now().plusHours(4), min = LocalDateTime.now()),
                status = Status.BEFORE)
        eventRepository.filtered(filter)
                .map { it.copy(status = Status.NOTIFIED) }
                .forEach {
                    it.guests.forEach { guest -> notifyGuest(guest, it.organizers, it) }
                    eventRepository.update(it)
                }
    }

    private suspend fun notifyGuest(guest: Participant, organizator: Participant, event: Event) {
        val notification = Notification(id = NotificationId(UUID.randomUUID().toString()),
                receiver = guest,
                sender = organizator,
                content = Content("${organizator.firstName}: Nie zapomnij o ${event.details.period.endTime} odbywa się ${event.name} "),
                state = State.NOT_ACTION,
                creationTime = CreationTime(LocalDateTime.now()),
                eventId = event.id)
        notificationRepository.save(notification)

    }


}